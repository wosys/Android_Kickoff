// IOnSongChange.aidl
package com.wintmain.wPlayer.aidl;
import com.wintmain.wPlayer.aidl.Song;

// Declare any non-default types here with import statements

interface IOnPlayStatusChangedListener {

    //自动播放时歌曲播放完成时回调
    void playStop(in Song which,int index,int status);

    //自动播放时歌曲开始播放时回调
    void playStart(in Song which,int index,int status);

}
