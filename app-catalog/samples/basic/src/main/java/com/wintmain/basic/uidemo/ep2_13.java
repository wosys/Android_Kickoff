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

package com.wintmain.basic.uidemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep2_13 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        Button button1 = new Button(this);
        Button button2 = new Button(this);
        Button button3 = new Button(this);
        Button button4 = new Button(this);
        button1.setText("按钮对话框");
        button2.setText("列表对话框");
        button3.setText("列表+按钮对话框");
        button4.setText("多选+按钮对话框");
        linearLayout.addView(button1);
        linearLayout.addView(button2);
        linearLayout.addView(button3);
        linearLayout.addView(button4);

        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alertDialog =
                                new AlertDialog.Builder(ep2_13.this).create();
                        alertDialog.setIcon(R.drawable.img01);
                        alertDialog.setTitle("提示一：");
                        alertDialog.setMessage("取消、中立和确定");
                        //        消极的
                        alertDialog.setButton(
                                DialogInterface.BUTTON_NEGATIVE,
                                "取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("对话框", "点击了取消");
                                    }
                                });
                        //        积极的
                        alertDialog.setButton(
                                DialogInterface.BUTTON_POSITIVE,
                                "确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("对话框", "点击了确定");
                                    }
                                });
                        //        中立
                        alertDialog.setButton(
                                DialogInterface.BUTTON_NEUTRAL,
                                "中立",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("对话框", "点击了中立");
                                    }
                                });
                        alertDialog.show();
                    }
                });

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] strings = {"第一条", "第二条", "第三条", "第四条"};
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ep2_13.this);
                        alertDialog.setTitle("提示二：");
                        alertDialog.setItems(
                                strings,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("对话框", "onClick: " + strings[which]);
                                    }
                                });
                        alertDialog.create().show();
                    }
                });
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] strings = {"第一条", "第二条", "第三条", "第四条"};
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ep2_13.this);
                        alertDialog.setTitle("提示三：");
                        alertDialog.setSingleChoiceItems(
                                strings,
                                0,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("对话框", "onClick: " + strings[which]);
                                    }
                                });
                        alertDialog.setPositiveButton("确定", null);
                        alertDialog.create().show();
                    }
                });
        button4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean[] items = {false, false, true, true};
                        String[] strings = {"第一条", "第二条", "第三条", "第四条"};
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ep2_13.this);
                        alertDialog.setTitle("提示三：");
                        alertDialog.setMultiChoiceItems(
                                strings,
                                items,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog, int which, boolean isChecked) {
                                        items[which] = isChecked;
                                    }
                                });
                        alertDialog.setPositiveButton(
                                "确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String result = "";
                                        for (int i = 0; i < items.length; i++) {
                                            if (items[i]) {
                                                result += strings[i] + "、";
                                            }
                                        }
                                        if (!"".equals(result)) {
                                            result = result.substring(0, result.length() - 1);
                                            Log.i("对话框", result);
                                        }
                                    }
                                });
                        alertDialog.create().show();
                    }
                });
    }
}
