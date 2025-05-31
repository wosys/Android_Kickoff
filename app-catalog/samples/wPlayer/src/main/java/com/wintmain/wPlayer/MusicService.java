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

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Description 音乐播放service，用于控制音乐的播放暂停，上一首下一首，播放模式等功能
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date
 */
public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private static final int NOTIFY_ID = 1;
    //media player
    static MediaPlayer player;
    private final IBinder musicBind = new MusicBinder();
    private final ArrayList<Song> recent = new ArrayList<Song>();
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosn;
    private boolean shuffle = false;
    private Random rand;

    public void onCreate() {
        //create the service
        super.onCreate();
        //初始化位置
        songPosn = 0;
        //create player
        player = new MediaPlayer();

        initMusicPlayer();

        rand = new Random();

    }

    public ArrayList<Song> newlist() {
        return recent;
    }


    public boolean setShuffle() {
        shuffle = !shuffle;
        return shuffle;
    }

    public void initMusicPlayer() {
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }


    public void setList(ArrayList<Song> theSongs) {
        songs = theSongs;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return musicBind;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            //TODO
            //每次播放完
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //start playback
        mediaPlayer.start();

//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
//                    "YOUR_CHANNEL_NAME",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
//            mNotificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder
//        (getApplicationContext(), "YOUR_CHANNEL_ID")
//                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
//                .setContentTitle("Playing") // title for notification
//                .setContentText(songTitle)// message for notification
//                .setAutoCancel(true); // clear notification after click
//        Intent intent = new Intent(getApplicationContext(), LocalActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent
//        .FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pi);
//        mNotificationManager.notify(0, mBuilder.build());


    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    public void playSong() {
        //play a song
        player.reset();

        //get song
        Song playSong = songs.get(songPosn);


        long id = playSong.getID();
        String name = playSong.getTitle();
        String singer = playSong.getArtist();

        recent.add(new Song(id, name, singer));


        String songTitle = playSong.getTitle();

        //get id
        long currSong = playSong.getID();
        //设置 uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);

        try {
            player.setDataSource(getApplicationContext(), trackUri);
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }

    public void setSong(int songIndex) {
        songPosn = songIndex;
    }

    public int getPosn() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        return player.getDuration();
    }

    public boolean isPng() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        player.pause();
    }

    public void seek(int posn) {
        player.seekTo(posn);
    }

    public void go() {
        player.start();
    }

    public void playPrev() {
        songPosn--;
        if (songPosn < 0) {
            songPosn = songs.size() - 1;  //&lt;
        }
        playSong();
    }

    //skip to next
    public void playNext() {
        if (shuffle) {
            int newSong = songPosn;
            while (newSong == songPosn) {
                newSong = rand.nextInt(songs.size());
            }
            songPosn = newSong;
        } else {
            songPosn++;
            if (songPosn >= songs.size()) {
                songPosn = 0;
            }
        }
        playSong();
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}

