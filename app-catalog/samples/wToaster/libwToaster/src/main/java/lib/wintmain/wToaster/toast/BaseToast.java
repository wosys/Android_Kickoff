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

package lib.wintmain.wToaster.toast;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import lib.wintmain.wToaster.R;

/**
 * @Description @Author wintmain
 * @mailto wosintmain@gmail.com @Date 2022-11-27 16:52:29
 */
abstract class BaseToast extends Toast implements Runnable {

    // 显示延迟时间，避免重复点击
    static final int SHOW_DELAY_MILLIS = 300;

    // Toast 处理消息线程
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    // 吐司消息View
    private TextView mMessageView;

    // 吐司显示的文本
    private CharSequence mText;

    BaseToast(Application application) {
        super(application);
    }

    /** 递归获取ViewGroup中的TextView对象 */
    private static TextView findTextView(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View view = group.getChildAt(i);
            if ((view instanceof TextView)) {
                return (TextView) view;
            } else if (view instanceof ViewGroup) {
                TextView textView = findTextView((ViewGroup) view);
                if (textView != null) {
                    return textView;
                }
            }
        }
        return null;
    }

    @Override
    public void setView(View view) {
        super.setView(view);
        if (view instanceof TextView) {
            mMessageView = (TextView) view;
            return;
        } else if (view.findViewById(R.id.toast_main_text_view_id) instanceof TextView) {
            mMessageView = ((TextView) view.findViewById(R.id.toast_main_text_view_id));
            return;
        } else if (view instanceof ViewGroup) {
            if ((mMessageView = findTextView((ViewGroup) view)) != null) {
                return;
            }
        }
        // 如果设置的布局没有包含一个 TextView 则抛出异常，必须要包含一个 TextView 作为 Message View
        throw new IllegalArgumentException("The layout must contain a TextView");
    }

    /***
     * 获取当前 Handler 对象
     */
    Handler getHandler() {
        return mHandler;
    }

    /** 获取当前的消息 View */
    TextView getMessageView() {
        return mMessageView;
    }

    /** 获取当前欲显示的文本 */
    CharSequence getText() {
        return mText;
    }

    @Override
    public void setText(CharSequence s) {
        // 记录本次吐司欲显示的文本
        mText = s;
    }
}
