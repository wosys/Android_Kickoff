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

import android.app.AppOpsManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import lib.wintmain.wToaster.R;
import lib.wintmain.wToaster.style.ToastBlackStyle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** desc : Toast工具类 */
public final class ToastUtils {
    public static final String TAG = "ToastUtils";

    private static IToastStyle sDefaultStyle;

    private static Toast sToast;

    /**
     * 初始化ToastUtils，建议在Application中初始化
     *
     * @param application 应用的上下文
     */
    public static void init(Application application) {
        // 检查默认样式是否为空，如果是就创建一个默认样式
        if (sDefaultStyle == null) {
            sDefaultStyle = new ToastBlackStyle();
        }
        //        //如果这个上下文不是全局的上下文，就自动换成全局的上下文
        //        if (context != context.getApplicationContext()) {
        //            context = context.getApplicationContext();
        //        }
        //
        //        GradientDrawable drawable = new GradientDrawable();
        //        drawable.setColor(sDefaultStyle.getBackgroundColor());//设置背景色
        //        drawable.setCornerRadius(DimensUtils.dp2px(context,
        // sDefaultStyle.getCornerRadius()));//设置圆角
        //
        //        TextView textView = new TextView(context);
        //        textView.setTextColor(sDefaultStyle.getTextColor());
        //        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensUtils.sp2px(context,
        // sDefaultStyle.getTextSize()));
        //        textView.setPadding(DimensUtils.dp2px(context, sDefaultStyle.getPaddingLeft()),
        // DimensUtils.dp2px(context, sDefaultStyle.getPaddingTop()),
        //                DimensUtils.dp2px(context, sDefaultStyle.getPaddingRight()),
        // DimensUtils.dp2px(context, sDefaultStyle.getPaddingBottom()));
        //        textView.setLayoutParams(new
        // ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        // ViewGroup.LayoutParams.WRAP_CONTENT));
        //        //textView.setBackgroundDrawable(drawable);
        //        //设置兼容，minSDK需要一定程度的小点
        //        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
        //            textView.setBackground(drawable);
        //        }else {
        //            textView.setBackgroundDrawable(drawable);
        //        }
        //
        //        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
        //            textView.setZ(sDefaultStyle.getZ());//设置Z轴阴影
        //        }
        //
        //        if (sDefaultStyle.getMaxLines() > 0) {
        //            textView.setMaxLines(sDefaultStyle.getMaxLines());//设置最大显示行数
        //        }
        //
        //        sToast = new XToast(context);
        //        sToast.setGravity(sDefaultStyle.getGravity(), sDefaultStyle.getXOffset(),
        // sDefaultStyle.getYOffset());
        //        sToast.setView(textView);
        // 判断有没有通知栏权限
        if (isNotificationEnabled(application)) {
            sToast = new XToast(application);
        } else {
            sToast = new SupportToast(application);
        }

        sToast.setGravity(
                sDefaultStyle.getGravity(), sDefaultStyle.getXOffset(), sDefaultStyle.getYOffset());
        sToast.setView(createTextView(application));
    }

    /**
     * 显示一个对象的吐司
     *
     * @param object 对象
     */
    public static void show(Object object) {
        //        if (object != null) {
        //            show(object.toString());
        //        }else {
        //            show("null");
        //        }
        show(object != null ? object.toString() : "null");
    }

    /**
     * 显示一个吐司
     *
     * @param id 如果传入的是正确的string id就显示对应字符串 如果不是则显示一个整数的sting
     */
    public static void show(int id) {
        checkToastState();
        try {
            // 如果这是一个资源id
            show(sToast.getView().getContext().getResources().getText(id));
        } catch (Resources.NotFoundException ignored) {
            // 如果这是一个int类型
            show(String.valueOf(id));
        }
    }

    /**
     * 显示一个吐司
     *
     * @param text 需要显示的文本
     */
    public static void show(CharSequence text) {
        checkToastState();
        if (text == null || text.equals("")) {
            return;
        }

        // 如果显示的文字超过了10个就显示长吐司，否则显示短吐司
        if (text.length() > 20) {
            sToast.setDuration(Toast.LENGTH_LONG);
        } else {
            sToast.setDuration(Toast.LENGTH_SHORT);
        }

        //        //子线程中异常情况处理
        //        try {
        ////            ((TextView) sToast.getView()).setText(text);
        ////            sToast.show();
        ////        } catch (RuntimeException e) {
        ////            try {
        //            //判断是否在主线程中执行
        //            if(Looper.myLooper() == Looper.getMainLooper()){
        //                sToast.setText(text);
        //                sToast.show();
        //            }else {
        //                //在子线程中处理
        //                Looper.prepare();
        //                //((TextView) sToast.getView()).setText(text);
        //                sToast.setText(text);
        //                sToast.show();
        //                Looper.loop();
        //            }
        //        } catch (Exception ignored) {}
        sToast.setText(text);
        sToast.show();
    }

    /** 获取当前Toast对象 */
    public static Toast getToast() {
        return sToast;
    }

    /*
     * 给当前的Toast设置新的布局
     */
    public static void setView(Context context, int layoutId) {
        if (context != context.getApplicationContext()) {
            context = context.getApplicationContext();
        }
        setView(View.inflate(context, layoutId, null));
    }

    /** 给当前Toast布置新布局 */
    public static void setView(View view) {
        checkToastState();

        if (view == null) {
            throw new IllegalArgumentException("View can't be empty");
        }
        //        sToast.setView(view);
        if (sToast != null) {
            sToast.cancel();
            sToast.setView(view);
        }
    }

    /**
     * 初始化Toast样式 统一全局的Toast样式，在Applition初始化
     *
     * @param style 样式实现类
     */
    public static void initStyle(IToastStyle style) {
        ToastUtils.sDefaultStyle = style;
        // 如果吐司已经创建，就重新初始化吐司
        if (sToast != null) {
            // 取消原来的
            sToast.cancel();
            // 重新初始化
            sToast.setView(createTextView(sToast.getView().getContext().getApplicationContext()));
        }
    }

    private static void checkToastState() {
        if (sToast == null) {
            // 工具类还没有初始化，必须先调用init()
            throw new IllegalStateException("must use init() to init");
        }
    }

    /** 生成默认的 TextView 对象 */
    private static TextView createTextView(Context context) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(sDefaultStyle.getBackgroundColor()); // 设置背景色
        drawable.setCornerRadius(dp2px(context, sDefaultStyle.getCornerRadius())); // 设置圆角

        TextView textView = new TextView(context);
        textView.setId(R.id.toast_main_text_view_id);
        textView.setTextColor(sDefaultStyle.getTextColor());
        textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX, sp2px(context, sDefaultStyle.getTextSize()));
        textView.setPadding(
                dp2px(context, sDefaultStyle.getPaddingLeft()),
                dp2px(context, sDefaultStyle.getPaddingTop()),
                dp2px(context, sDefaultStyle.getPaddingRight()),
                dp2px(context, sDefaultStyle.getPaddingBottom()));
        textView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        // setBackground API版本兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackground(drawable);
        } else {
            textView.setBackgroundDrawable(drawable);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textView.setZ(sDefaultStyle.getZ()); // 设置 Z 轴阴影
        }

        if (sDefaultStyle.getMaxLines() > 0) {
            textView.setMaxLines(sDefaultStyle.getMaxLines()); // 设置最大显示行数
        }

        return textView;
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    private static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param spValue sp值
     * @return px值
     */
    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 检查通知栏权限有没有开启 参考SupportCompat包中的：
     * NotificationManagerCompat.from(context).areNotificationsEnabled();
     */
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                    .areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps =
                    (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            try {
                Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod =
                        appOpsClass.getMethod(
                                "checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue =
                        appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
            } catch (NoSuchMethodException
                     | NoSuchFieldException
                     | InvocationTargetException
                     | IllegalAccessException
                     | RuntimeException
                     | ClassNotFoundException ignored) {
                return true;
            }
        } else {
            return true;
        }
    }

    /* 取消吐司的显示
     */
    public void cancel() {
        checkToastState();
        sToast.cancel();
    }
}
