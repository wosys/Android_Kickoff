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

import android.view.View
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentHttpsCertificateBinding
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.okhttp.setSSLCertificate
import lib.wintmain.libwNet.okhttp.trustSSLCertificate
import lib.wintmain.libwNet.utils.scopeNetLife
import okhttp3.OkHttpClient

class HttpsCertificateFragment :
    EngineFragment<FragmentHttpsCertificateBinding>(R.layout.fragment_https_certificate) {

    override fun initView() {
        binding.btnTrustCertificate.setOnClickListener(this::trustAllCertificate)
        binding.btnImportCertificate.setOnClickListener(this::importCertificate)
    }

    override fun initData() {
    }

    /**
     * 信任全部证书
     * 大部分情况下还是建议在Application中配置一次全局的证书
     */
    private fun trustAllCertificate(view: View) {
        scopeNetLife {
            binding.tvResponse.text = Get<String>("https://github.com/liangjingkanji/Net/") {
                // 构建新的客户端
                okHttpClient = OkHttpClient.Builder().trustSSLCertificate().build()
            }.await()
        }
    }

    /**
     * 导入私有证书
     */
    private fun importCertificate(view: View) {
        scopeNetLife {
            Get<String>("https://github.com/liangjingkanji/Net/") {
                // 使用现在客户端
                setClient {
                    val privateCertificate = resources.assets.open("https.certificate")
                    setSSLCertificate(privateCertificate)
                }
            }.await()
        }.catch {
            toast("作者没有证书, 只是演示代码, O(∩_∩)O哈哈~")
        }
    }
}