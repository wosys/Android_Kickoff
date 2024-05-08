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

package com.wintmain.basic.prejob.uiDemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.basic.R

@Sample(
    name = "1. xml Layout",
    description = "利用 xml 文件设置布局文件",
    documentation = "",
    tags = ["A-Self_demos"],
)
class XmlToLayout : Fragment(R.layout.ep1_1)

@Sample(
    name = "2. code Layout",
    description = "利用 code 设置布局文件",
    documentation = "",
    tags = ["A-Self_demos"],
)
class CodeToLayout : Fragment() {
    private var textView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 创建帧布局管理器
        val frameLayout = context?.let { FrameLayout(it) }
        // 设置背景
        if (frameLayout != null) {
            frameLayout.background =
                ResourcesCompat.getDrawable(
                    this.resources, R.drawable.ic_launcher_foreground,
                    null
                )
        }
        // 创建一个TextView组件text1,设置其文字大小和颜色，并将其添加到布局管理器中
        val text1 = TextView(context)
        text1.text = "在代码中控制UI界面" // 设置显示的文字
        text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24f) // 设置文字大小，单位为像素
        text1.setTextColor(Color.rgb(1, 1, 1)) // 设置文字的颜色
        frameLayout?.addView(text1) // 将 text1添加到布局管理器中

        // 实例化textView组件，设置其显示文字、文字大小、颜色和布局
        textView = TextView(context)
        textView!!.text = "单击进入Android…" // 设置显示文字
        textView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24f) // 设置文字大小，单位为像素
        textView!!.setTextColor(Color.rgb(1, 1, 1)) // 设置文字的颜色
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ) // 创建保存布局参数的对象
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL // 设置居中显示
        textView!!.layoutParams = params // 设置布局参数

        frameLayout?.addView(textView) // 将 textView 添加到布局管理器中

        return frameLayout // 返回创建的布局
    }
}

/**
 * Java
public class EpOne_1 extends AppCompatActivity {

    public TextView text2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. xml方式
//        setContentView(R.layout.ep1_1);

        // 2. code方式
        // 创建帧布局管理器
        FrameLayout frameLayout = new FrameLayout(this);
        // 设置背景
        frameLayout.setBackground(
                ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_launcher_foreground,
                        null));
        // 设置在Activity中显示frameLayout
        setContentView(frameLayout);
        // 创建一个TextView组件text1,设置其文字大小和颜色，并将其添加到布局管理器中
        TextView text1 = new TextView(this);
        text1.setText("在代码中控制UI界面"); // 设置显示的文字
        text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24); // 设置文字大小，单位为像素
        text1.setTextColor(Color.rgb(1, 1, 1)); // 设置文字的颜色
        frameLayout.addView(text1); // 将 text1添加到布局管理器中

        // 实例化text2组件，设置其显示文字、文字大小、颜色和布局
        text2 = new TextView(this);
        text2.setText("单击进入Android…"); // 设置显示文字
        text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24); // 设置文字大小，单位为像素
        text2.setTextColor(Color.rgb(1, 1, 1)); // 设置文字的颜色
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT); // 创建保存布局参数的对象
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL; // 设置居中显示
        text2.setLayoutParams(params); // 设置布局参数
    }
}
*/

@Sample(
    name = "3. Rabbit Listener",
    description = "兔子布局 & 监听器",
    documentation = "",
    tags = ["A-Self_demos"],
)
class RabbitLayout : AppCompatActivity() {
    // 注意 AppCompatActivity 要在manifest里注册
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_4)

        val frameLayout: FrameLayout = findViewById(R.id.mylayout)
        // Attention here.
        val rabbit = RabbitView(this@RabbitLayout)
/*        rabbit.setOnTouchListener { _, event ->
            rabbit.bitmapX = event.x
            rabbit.bitmapY = event.y
            rabbit.invalidate()
            Toast.makeText(baseContext, "You touch this rabbit.", LENGTH_SHORT).show()
            true
        }*/

        rabbit.setOnLongClickListener{
            Toast.makeText(baseContext, "Long click.", LENGTH_SHORT).show()
            true
        }
        frameLayout.addView(rabbit)
    }
}

class RabbitView(context: Context) : View(context) {
    var bitmapX = 0f
    var bitmapY = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img06)
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint)
        if (bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}

/**
* Java
public class EpOne_4 extends Activity {
    // AppCompatActivity -> 会有title bar，还有其他的一些

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_4);

        FrameLayout frameLayout = findViewById(R.id.mylayout); // 获取帧布局管理器
        final RabbitView rabbit = new RabbitView(EpOne_4.this); // 创建并实例化RabbitView类
        // 为小兔子添加触摸事件监听器
        rabbit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rabbit.bitmapX = event.getX(); // 设置小兔子显示位置的X坐标
                rabbit.bitmapY = event.getY(); // 设置小兔子显示位置的Y坐标
                rabbit.invalidate(); // 重绘rabbit组件
                Toast.makeText(getBaseContext(), "You touch this rabbit.", LENGTH_SHORT).show();
                return true;
            }
        });
        frameLayout.addView(rabbit); // 将rabbit添加到布局管理器中
    }
}

class RabbitView extends View {
    public float bitmapX; // 小兔子显示位置的X坐标
    public float bitmapY; // 小兔子显示位置的Y坐标

    public RabbitView(Context context) { // 重写构造方法
        super(context);
        bitmapX = 0;
        bitmapY = 0;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(); // 创建并实例化Paint的对象
        Bitmap bitmap = decodeResource(this.getResources(), R.drawable.img06); // 根据图片生成位图对象
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint); // 绘制小兔子
        if (bitmap.isRecycled()) { // 判断图片是否回收
            bitmap.recycle(); // 强制回收图片
        }
    }
}
*/
