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

package com.wintmain.foundation.prejob.extDemo;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.wintmain.foundation.R;

public class ep5_1 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LinearLayout linearLayout = new LinearLayout(this);
//        setContentView(linearLayout);
//
//        TextView textView1 = new TextView(this);
//        textView1.setText(this.getResources().getString(R.string.title2));
//        textView1.setTextColor(this.getResources().getColor(R.color.title2));
//        textView1.setTextSize(this.getResources().getDimension(R.dimen.title2));
//        linearLayout.addView(textView1);
        setContentView(R.layout.ep5_1);
    }
}
