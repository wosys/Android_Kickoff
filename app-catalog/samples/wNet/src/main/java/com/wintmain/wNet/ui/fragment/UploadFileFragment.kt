/*
 * Copyright 2023-2025 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.wNet.ui.fragment

import android.app.Activity
import android.net.Uri
import com.drake.engine.base.EngineFragment
import com.wintmain.wNet.R
import com.wintmain.wNet.contract.AlbumSelectContract
import com.wintmain.wNet.databinding.FragmentUploadFileBinding
import lib.wintmain.libwNet.Post
import lib.wintmain.libwNet.component.Progress
import lib.wintmain.libwNet.interfaces.ProgressListener
import lib.wintmain.libwNet.utils.TipUtils
import lib.wintmain.libwNet.utils.scopeNetLife
import java.io.File

class UploadFileFragment :
    EngineFragment<FragmentUploadFileBinding>(R.layout.fragment_upload_file) {
    private val albumSelectLauncher = registerForActivityResult(AlbumSelectContract()) {
        when (it.code) {
            Activity.RESULT_CANCELED -> TipUtils.toast("取消图片选择")
            Activity.RESULT_OK -> uploadUri(it.uri)
        }
    }

    override fun initView() {
        binding.btnFile.setOnClickListener {
            uploadFile()
        }
        binding.btnUri.setOnClickListener {
            albumSelectLauncher.launch(null)
        }
    }

    private fun uploadFile() {
        scopeNetLife {
            Post<String>(com.wintmain.wNet.constants.Api.UPLOAD) {
                param("file", getRandomFile())
                addUploadListener(object : ProgressListener() {
                    override fun onProgress(p: Progress) {
                        binding.seek.post {
                            binding.seek.progress = p.progress()
                            binding.tvProgress.text =
                                "上传进度: ${p.progress()}% 上传速度: ${p.speedSize()}     " + "\n\n文件大小: ${p.totalSize()}  已上传: ${p.currentSize()}  剩余大小: ${p.remainSize()}" + "\n\n已使用时间: ${p.useTime()}  剩余时间: ${p.remainTime()}"
                        }
                    }
                })
            }.await()
        }
    }

    private fun uploadUri(uri: Uri?) {
        scopeNetLife {
            Post<String>(com.wintmain.wNet.constants.Api.UPLOAD) {
                param("file", uri)
                addUploadListener(object : ProgressListener() {
                    override fun onProgress(p: Progress) {
                        binding.seek.post {
                            binding.seek.progress = p.progress()
                            binding.tvProgress.text =
                                "上传进度: ${p.progress()}% 上传速度: ${p.speedSize()}     " + "\n\n文件大小: ${p.totalSize()}  已上传: ${p.currentSize()}  剩余大小: ${p.remainSize()}" + "\n\n已使用时间: ${p.useTime()}  剩余时间: ${p.remainTime()}"
                        }
                    }
                })
            }.await()
        }
    }

    /** 生成指定大小的随机文件 */
    private fun getRandomFile(): File {
        val file = File(requireContext().filesDir, "uploadFile.apk")
        // 本演示项目的Mock服务不支持太大的文件, 可能会OOM溢出, 实际接口请求不存在
        com.wintmain.wNet.utils.RandomFileUtils.createRandomFile(
            file,
            30,
            com.wintmain.wNet.utils.RandomFileUtils.FileSizeUnit.MB
        )
        return file
    }

    override fun initData() {
    }
}
