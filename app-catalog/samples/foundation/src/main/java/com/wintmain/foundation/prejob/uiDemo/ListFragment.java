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

package com.wintmain.foundation.prejob.uiDemo;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.wintmain.foundation.R;

public class ListFragment extends android.app.ListFragment {
    boolean dualPane; // 是否在一屏上同时显示列表和详细内容
    int curCheckPosition = 0; // 当前选择的索引位置

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 为列表设置适配器
        setListAdapter(
                new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_checked, Data.TITLES));
        // 得到布局文件中添加的帧布局管理器
        View detail = getActivity().findViewById(R.id.detail);
        // 判断是否在一屏上同时显示列表和详细内容
        dualPane = detail != null && detail.getVisibility() == View.VISIBLE;
        if (savedInstanceState != null) {
            // 更新当前索引的位置
            curCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }
        if (dualPane) { // 在同一屏上同时显示列表和详细内容
            // 如果是横屏，则将list设置成单选模式
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(curCheckPosition); // 显示详细内容
        }
    }

    // 重写方法，保存当前选中的索引值
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", curCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position); // 调用方法显示详细内容
    }

    void showDetails(int aIndex) {
        curCheckPosition = aIndex; // 更新保存当前索引位置的变量的值为当前选中值
        // 如果是横屏状态
        if (dualPane) {
            getListView().setItemChecked(aIndex, true); // 设置选中列表项为选中状态
            DetailFragment details =
                    (DetailFragment)
                            getFragmentManager()
                                    .findFragmentById(R.id.detail); // 获取用于显示详细内容的Fragment
            // 如果 details的fragment为空或者是它的当前显示的索引不等于选中的索引
            if (details == null || details.getShownIndex() != aIndex) {
                // 创建一个detailFragment的实例对象，用于显示当前选择项对应的详细内容
                details = DetailFragment.newInstance(aIndex);
                // 要在Activity中管理fragment，需要使用FragmentManager
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                // 将这个实例化对象替换到原先的内容
                fragmentTransaction.replace(R.id.detail, details);
                // 设置转换效果
                fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit(); // 提交事务
            }

        } else { // 竖屏就进行跳转（在一屏上只能显示列表或详细内容中的一个内容时，使用一个新的Activity显示详细内容）
            Intent intent = new Intent(getActivity(), ep3_7.DetailActivity.class);
            intent.putExtra("aIndex", aIndex); // 设置一个要传递的参数
            startActivity(intent); // 开启一个指定的activity
        }
    }
}
