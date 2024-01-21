// IOnSongChange.aidl
package com.wintmain.mymusicplayer.aidl;
import com.wintmain.mymusicplayer.aidl.Song;

// Declare any non-default types here with import statements

interface IOnPlayListChangedListener {

    void onPlayListChange(in Song current,int index,int id);
}
