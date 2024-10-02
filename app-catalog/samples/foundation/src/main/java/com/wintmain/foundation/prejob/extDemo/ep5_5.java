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

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wintmain.foundation.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ep5_5 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        TextView textView = new TextView(this);
        textView.setTextSize(28);
        textView.setText("正在读取文件...");
        linearLayout.addView(textView);

        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.customers); // 获取xml文档
        StringBuilder stringBuilder = new StringBuilder(""); // 创建一个空的字符串构造器

        try {
            // 如果没有到xml文档的结尾
            while (xmlResourceParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xmlResourceParser.getEventType() == XmlPullParser.START_TAG) {
                    // 判断是否是开始标志
                    String tagName = xmlResourceParser.getName(); // 获取标记名
                    if (tagName.equals("customer")) {
                        // 如果标记名是customer
                        stringBuilder.append(
                                "姓名：" + xmlResourceParser.getAttributeValue(1) + " "); // 获取客户姓名
                        stringBuilder.append(
                                "联系电话：" + xmlResourceParser.getAttributeValue(2) + " ");
                        stringBuilder.append("E-mail：" + xmlResourceParser.getAttributeValue(0));
                        stringBuilder.append("\n");
                    }
                }
                xmlResourceParser.next();
            }
            textView.setText(stringBuilder.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
