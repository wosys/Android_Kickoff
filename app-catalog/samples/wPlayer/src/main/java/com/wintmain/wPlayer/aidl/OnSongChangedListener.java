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

/**
 * 用户主动切换歌曲时回调，包含如下几种情况：
 * 1 播放相同播放列表中指定曲目 2 切换到 前一首
 * 3 切换到后一首 4 切换播放列表
 */
public abstract class OnSongChangedListener extends IOnSongChangedListener.Stub {

    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    @Override
    /** 该方法在服务端线程的 Binder 线程池中运行，客户端调用时不能操作 UI 控件 */
    public abstract void onSongChange(Song which, int index, boolean isNext);
}
