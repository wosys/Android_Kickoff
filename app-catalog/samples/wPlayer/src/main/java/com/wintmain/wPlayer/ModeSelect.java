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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.catalog.framework.annotations.Sample;
import com.wintmain.wPlayer.app.InternetActivity;
import com.wintmain.wPlayer.app.LocalActivity;
import com.wintmain.wPlayer.app.StartActivity;
import lib.wintmain.wToaster.toast.ToastUtils;

/**
 * @Description 选择本地音乐播放器和还是在线音乐播放器，本地播放器会依赖MediaPlayer类读取本机的音频文件进行播放
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date 2022-06-01 11:00:03
 */
@SuppressWarnings("unchecked") // 编译报错的时候可以用这个
@Sample(name = "MyMusicPlayer", description = "音乐播放器", tags = "A-Self_demos")
public class ModeSelect extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtils.init(this.getApplication());
        setContentView(R.layout.music_mode);
        Button play1 = this.findViewById(R.id.localplay);
        Button play2 = this.findViewById(R.id.internetplay);
        Button play3 = this.findViewById(R.id.placeholderplay);
        play1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent().setClass(ModeSelect.this, LocalActivity.class));
                    }
                });

        play2.setOnClickListener(
                v -> startActivity(new Intent().setClass(ModeSelect.this, InternetActivity.class)));

        play3.setOnClickListener(
                v -> startActivity(new Intent().setClass(ModeSelect.this, StartActivity.class)));
    }
}
