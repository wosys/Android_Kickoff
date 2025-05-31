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

package com.wintmain.wPlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditPlaylist extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;

    public EditPlaylist(Context c, ArrayList<Song> theSongs) {
        songs = theSongs;
        songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // We must create a View:
            convertView = songInf.inflate(R.layout.editplaylist, parent, false);
        }
        // map to song layout
        LinearLayout songLay = (LinearLayout) convertView;
        // get title and artist views
        TextView songView = songLay.findViewById(R.id.song_title);
        TextView artistView = songLay.findViewById(R.id.song_artist);
        // get song using position
        Song currSong = songs.get(position);
        // get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        // set position as tag

        songLay.setTag(position);
        return songLay;
    }
}
