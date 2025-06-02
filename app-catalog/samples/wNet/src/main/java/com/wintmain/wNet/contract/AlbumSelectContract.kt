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

package com.wintmain.wNet.contract

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract

class AlbumSelectContract : ActivityResultContract<Unit?, AlbumSelectContract.AlbumSelectResult>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        return intent
    }

    class AlbumSelectResult(val code: Int, val uri: Uri?)

    override fun parseResult(resultCode: Int, intent: Intent?): AlbumSelectResult {
        return AlbumSelectResult(resultCode, intent?.data)
    }
}