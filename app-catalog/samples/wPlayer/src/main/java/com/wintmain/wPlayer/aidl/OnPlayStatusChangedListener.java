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

package com.wintmain.wPlayer.aidl;

import android.os.IBinder;

/** 用户手动暂停，播放歌曲时回调 */
public abstract class OnPlayStatusChangedListener extends IOnPlayStatusChangedListener.Stub {

    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    /**
     * 1 自动播放时开始播放曲目时回调 2 继续播放，开始播放时回调
     *
     * @param song   当前开始播放曲目
     * @param index  播放列表下标
     * @param status 播放状态
     */
    @Override
    public abstract void playStart(Song song, int index, int status);

    /**
     * 1 自动播放时播放曲目播放完成时回调，一般情况下该方法调用后{@link #playStart(Song, int, int)}将会被调用 2 暂停，停止播放时回调
     *
     * @param song   当前播放曲目
     * @param index  播放列表下标
     * @param status 播放状态
     */
    @Override
    public abstract void playStop(Song song, int index, int status);
}
