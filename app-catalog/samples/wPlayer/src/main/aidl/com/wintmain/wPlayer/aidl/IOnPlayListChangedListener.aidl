// IOnSongChange.aidl
package com.wintmain.wPlayer.aidl;
import com.wintmain.wPlayer.aidl.Song;

// Declare any non-default types here with import statements

interface IOnPlayListChangedListener {

    void onPlayListChange(in Song current,int index,int id);
}
