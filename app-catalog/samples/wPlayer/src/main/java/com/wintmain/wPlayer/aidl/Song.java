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

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    public static final Creator<Song> CREATOR =
            new Creator<Song>() {
                @Override
                public Song createFromParcel(Parcel source) {
                    return new Song(source);
                }

                @Override
                public Song[] newArray(int size) {
                    return new Song[size];
                }
            };
    // 与客户端 DbSongInfo 中的 data 域对应，对于同一首歌曲（文件路径相同），两者应该相同
    public String path;

    public Song(String path) {
        this.path = path;
    }

    protected Song(Parcel in) {
        this.path = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Song song = (Song) o;

        return path.equals(song.path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
    }
}
