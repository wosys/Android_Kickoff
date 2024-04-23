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


package com.wintmain.basic.views.recyclerview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.catalog.framework.annotations.Sample;
import com.wintmain.basic.R;
import com.wintmain.basic.views.recyclerview.logger.Log;
import com.wintmain.basic.views.recyclerview.logger.LogFragment;
import com.wintmain.basic.views.recyclerview.logger.LogWrapper;
import com.wintmain.basic.views.recyclerview.logger.MessageOnlyLogFilter;

import java.util.Objects;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link androidx.fragment.app.Fragment} which can display a view.
 */
@Sample(name = "RecyclerView", description = "使用 RecyclerView 创建动态列表",
        tags = {"android-samples", "recyclerView"})
public class RecyclerViewActivity extends AppCompatActivity {

    public static final String TAG = "RecyclerViewActivity";

    private boolean mLogShown; // Whether the Log Fragment is currently shown

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeLogging();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(true);
        logToggle.setTitle(mLogShown ? "sample_hide_log" : "sample_show_log");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_toggle_log) {
            mLogShown = !mLogShown;
            ViewAnimator output = findViewById(R.id.sample_output);
            if (mLogShown) {
                output.setDisplayedChild(1);
            } else {
                output.setDisplayedChild(0);
            }
            supportInvalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a chain of targets that will receive log data
     */
    public void initializeLogging() {
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
        msgFilter.setNext(Objects.requireNonNull(logFragment).getLogView());

        Log.i(TAG, "Ready...RecyclerViewActivity");
    }
}
