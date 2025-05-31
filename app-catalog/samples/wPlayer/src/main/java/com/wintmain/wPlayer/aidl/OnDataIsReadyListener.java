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
import android.os.RemoteException;

public abstract class OnDataIsReadyListener extends IOnDataIsReadyListener.Stub {

    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    /** 在此之前服务端已经能够对监听者分发其他事件回调，而客户端想要与服务端交互（操作服务端），应在这里开始 */
    @Override
    public abstract void dataIsReady() throws RemoteException;
}
