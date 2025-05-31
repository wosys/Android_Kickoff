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

package com.wintmain.wBasis.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class People implements Parcelable {

    public int pId;
    public String pName;

    public People(Parcel in) {
        pId = in.readInt();
        pName = in.readString();
    }

    public People(int pId, String pName) {
        this.pId = pId;
        this.pName = pName;
    }

    public String getName() {
        return pName;
    }

    public void setName(String pName) {
        this.pName = pName;
    }

    public int getId() {
        return pId;
    }

    public void setId(int pId) {
        this.pId = pId;
    }

    // 内容描述功能由describeContents方法来完成
    // 几乎在所有情况下这方法都应该返回0，仅当当前对象中存在文件描述符时才返回1。
    @Override
    public int describeContents() {
        return 0;
    }

    // 序列化功能由writeToParcel方法来完成，最终是通过Parcel中的一系列write方法来完成的。
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(pId);
        dest.writeString(pName);
    }

    // 反序列化功能由CREATOR来完成，其内部标明了如何创建序列化对象和数组，
    // 并通过Parcel的一系列read方法来完成反序列化过程。
    public static final Parcelable.Creator<People> CREATOR = new Parcelable.Creator<>() {
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        public People[] newArray(int size) {
            return new People[size];
        }
    };


    @NonNull
    @Override
    public String toString() {
        return String.format("pId:%s, pName:%s", pId, pName);
    }
}
