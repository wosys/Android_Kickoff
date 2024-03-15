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

package com.wintmain.basic.uiDemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    // 创建一个DetailFragment的新实例，其中包括要传递的数据包
    public static DetailFragment newInstance(int index) {
        DetailFragment f = new DetailFragment();
        // 将index作为一个参数传递
        Bundle bundle = new Bundle(); // 实例化一个Bundle对象
        bundle.putInt("index", index); // 将索引值添加到Bundle对象中
        f.setArguments(bundle); // 将bundle对象作为Fragment的参数保存
        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0); // 获取要显示的列表项索引
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        ScrollView scroller = new ScrollView(getActivity()); // 创建一个滚动视图
        TextView text = new TextView(getActivity()); // 创建一个文本框对象
        text.setPadding(10, 10, 10, 10); // 设置内边距
        scroller.addView(text); // 将文本框对象添加到滚动视图中
        text.setText(Data.DETAIL[getShownIndex()]); // 设置文本框中要显示的文本
        return scroller;
    }
}
