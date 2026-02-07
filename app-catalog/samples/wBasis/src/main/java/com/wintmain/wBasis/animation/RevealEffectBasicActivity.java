/*
 * Copyright 2023-2026 wintmain
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

package com.wintmain.wBasis.animation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.catalog.framework.annotations.Sample;
import com.wintmain.wBasis.R;
import lib.wintmain.wBasis.logger.Log;
import lib.wintmain.wBasis.logger.LogFragment;
import lib.wintmain.wBasis.logger.LogWrapper;
import lib.wintmain.wBasis.logger.MessageOnlyLogFilter;


@Sample(name = "RevealEffectBasicActivity",
        description = "揭露动画，一种类似于水波纹效果的从一个点向周围扩散或者从四周向一个点集中的动画效果",
        tags = {"android-samples", "animation-samples"}
)
public class RevealEffectBasicActivity extends AppCompatActivity {
    public static final String TAG = "RevealEffectBasicActivity";
    private boolean isLogShow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_effect);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RevealEffectBasicFragment fragment = new RevealEffectBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(isLogShow ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toggle_log) {
            isLogShow = !isLogShow;
            ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
            if (isLogShow) {
                output.setDisplayedChild(1);
            } else {
                output.setDisplayedChild(0);
            }
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
