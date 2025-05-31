/*
 * Copyright 2023-2024 wintmain
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

package lib.wintmain.xplayer.play

/**
 * des 播放状态,需要注入到播放控制器中,用于播放状态的回调
 */
interface IPlayerStatus {

    /**
     * 缓冲更新
     */
    fun onBufferingUpdate(percent: Int)

    /**
     * 播放结束
     */
    fun onComplete()
}