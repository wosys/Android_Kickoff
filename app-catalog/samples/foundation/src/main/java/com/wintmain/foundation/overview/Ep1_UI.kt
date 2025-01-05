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

package com.wintmain.foundation.overview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.foundation.R
import java.util.Calendar

@Sample(
    name = "01. xml Layout",
    description = "利用 xml 文件设置布局文件",
    documentation = "",
    tags = ["A-Self_demos"],
)
class XmlToLayout : Fragment(R.layout.ep1_1)

@Sample(
    name = "02. code Layout",
    description = "利用 code 设置布局文件",
    documentation = "",
    tags = ["A-Self_demos"],
)
class CodeToLayout : Fragment() {
    private var textView: TextView? = null

    @SuppressLint("SetTextI18n")
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
    name = "03. Rabbit Listener",
    description = "兔子布局 & 监听器",
    documentation = "",
    tags = ["A-Self_demos"],
)
class RabbitLayout : AppCompatActivity() {
    // 注意 AppCompatActivity 要在manifest里注册
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_4)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

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

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class RabbitView(context: Context) : View(context) {
    private var bitmapX = 0f
    private var bitmapY = 0f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_badminton)
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

@Sample(
    name = "04. Register Member",
    description = "信息录入",
    documentation = "",
    tags = ["A-Self_demos"],
)
class RegisterMember : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_12)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button1 = findViewById<View>(R.id.button1) as Button
        button1.setOnClickListener {
            val nicknameET =
                findViewById<View>(R.id.nickname) as EditText // 获取会员昵称编辑框组件
            val nickname = nicknameET.getText().toString() // 获取输入的会员昵称
            val pwdET = findViewById<View>(R.id.pwd) as EditText // 获取密码编辑框组件
            val pwd = pwdET.getText().toString() // 获取输入的密码
            val emailET = findViewById<View>(R.id.email) as EditText // 获取E-mail编辑框组件
            val email = emailET.getText().toString() // 获取输入的E-mail地址
            val confirmpwdET =
                findViewById<View>(R.id.confirmpwd) as EditText // 获取确认密码编辑框组件
            val confirmpwd = confirmpwdET.getText().toString()
            if (confirmpwd.isNotEmpty() && confirmpwd == pwd) {
                Toast.makeText(
                    applicationContext,
                    "会员昵称:" + nickname + " 密码:" + pwd +
                        " E-mail地址:" + email, Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    applicationContext, "两次输入的密码不一致",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        val button2 = findViewById<View>(R.id.button2) as Button
        button2.setOnClickListener {
            val nicknameET =
                findViewById<View>(R.id.nickname) as EditText // 获取会员昵称编辑框组件
            nicknameET.setText("")
            val pwdET = findViewById<View>(R.id.pwd) as EditText // 获取密码编辑框组件
            pwdET.setText("")
            val confirmpwdET =
                findViewById<View>(R.id.confirmpwd) as EditText // 获取确认密码编辑框组件
            confirmpwdET.setText("")
            val emailET = findViewById<View>(R.id.email) as EditText // 获取E-mail编辑框组件
            emailET.setText("")
            Toast.makeText(applicationContext, "Reset success...", Toast.LENGTH_LONG).show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

/*
* Java
public class EpOne_12 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_12);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameET = (EditText) findViewById(R.id.nickname); // 获取会员昵称编辑框组件
                String nickname = nicknameET.getText().toString(); // 获取输入的会员昵称
                EditText pwdET = (EditText) findViewById(R.id.pwd); // 获取密码编辑框组件
                String pwd = pwdET.getText().toString(); // 获取输入的密码
                EditText emailET = (EditText) findViewById(R.id.email); // 获取E-mail编辑框组件
                String email = emailET.getText().toString(); // 获取输入的E-mail地址

                EditText confirmpwdET = (EditText) findViewById(R.id.confirmpwd); // 获取确认密码编辑框组件
                String confirmpwd = confirmpwdET.getText().toString();

                if (!confirmpwd.isEmpty() && confirmpwd.equals(pwd)) {
                    Toast.makeText(getApplicationContext(),
                            "会员昵称:" + nickname + " 密码:" + pwd +
                                    " E-mail地址:" + email, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameET = (EditText) findViewById(R.id.nickname); // 获取会员昵称编辑框组件
                nicknameET.setText("");
                EditText pwdET = (EditText) findViewById(R.id.pwd); // 获取密码编辑框组件
                pwdET.setText("");
                EditText confirmpwdET = (EditText) findViewById(R.id.confirmpwd); // 获取确认密码编辑框组件
                confirmpwdET.setText("");
                EditText emailET = (EditText) findViewById(R.id.email); // 获取E-mail编辑框组件
                emailET.setText("");

                Toast.makeText(getApplicationContext(), "Reset success...", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
*/

@Sample(
    name = "05. RadioGroup",
    description = "单选框",
    documentation = "",
    tags = ["A-Self_demos"],
)
class RadioGroupDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_14)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val sex = findViewById<RadioGroup>(R.id.radiogroup1)
        // Parameter 'radioGroup' is never used, could be renamed to _
        sex.setOnCheckedChangeListener { _, i -> // i -> checkedId
            val r = findViewById<RadioButton>(i)
            Toast.makeText(
                this@RadioGroupDemo, "单选按钮, 选择的是：" + r.getText(),
                LENGTH_SHORT
            ).show()
        }

        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener { selectClick() }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    // 配合xml文件里的属性 android:onClick="selectClick"（kt有问题）
    private fun selectClick() {
        val sex2 = findViewById<RadioGroup>(R.id.radiogroup1)
        for (i in 0 until sex2.childCount) {
            val r = sex2.getChildAt(i) as RadioButton // 根据索引值获取单选按钮的值
            if (r.isChecked) { // 判断是否选中
                Toast.makeText(this@RadioGroupDemo, "你选择的是：" + r.getText(),
                    LENGTH_SHORT).show()
                break // 跳出循环，单选
            }
        }
    }
}

/*
* Java
public class EpOne_14 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_14);

        RadioGroup sex = findViewById(R.id.radiogroup1);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // i -> checkedId
                RadioButton r = findViewById(i);
                Toast.makeText(EpOne_14.this, "单选按钮, 选择的是：" + r.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 配合xml文件里的属性 android:onClick="selectClick"
    public void selectClick(View view) {
        final RadioGroup sex2 = findViewById(R.id.radiogroup1);

        for (int i = 0; i < sex2.getChildCount(); i++) {
            RadioButton r = (RadioButton) sex2.getChildAt(i); // 根据索引值获取单选按钮的值
            if (r.isChecked()) { // 判断是否选中
                Toast.makeText(EpOne_14.this, "你选择的是：" + r.getText(), Toast.LENGTH_SHORT)
                        .show();
                break; // 跳出循环，单选
            }
        }
    }
}
*/


@Sample(
    name = "06. CheckBox",
    description = "复选框",
    documentation = "",
    tags = ["A-Self_demos"],
)
class CheckBoxDemo : AppCompatActivity() {
    private val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            Toast.makeText(
                this@CheckBoxDemo, "选中了" + buttonView.getText().toString(),
                LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_15)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val c1 = findViewById<CheckBox>(R.id.c1)
        val c2 = findViewById<CheckBox>(R.id.c2)
        val c3 = findViewById<CheckBox>(R.id.c3)
        c1.setOnCheckedChangeListener(listener)
        c2.setOnCheckedChangeListener(listener)
        c3.setOnCheckedChangeListener(listener)
        val button = findViewById<Button>(R.id.submit1)
        button.setOnClickListener {
            var answer = ""
            if (c1.isChecked) {
                answer += c1.getText().toString() + " "
            }
            if (c2.isChecked) {
                answer += c2.getText().toString() + " "
            }
            if (c3.isChecked) {
                answer += c3.getText().toString() + " "
            }
            val toast = Toast.makeText(this@CheckBoxDemo, "选中$answer", LENGTH_SHORT)
            toast.show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

/*
* Java
public class EpOne_15 extends AppCompatActivity {

    private CompoundButton.OnCheckedChangeListener listener =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(EpOne_15.this, "选中了" + buttonView.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_15);

        CheckBox c1 = findViewById(R.id.c1);
        CheckBox c2 = findViewById(R.id.c2);
        CheckBox c3 = findViewById(R.id.c3);

        c1.setOnCheckedChangeListener(listener);
        c2.setOnCheckedChangeListener(listener);
        c3.setOnCheckedChangeListener(listener);

        Button button = findViewById(R.id.submit1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = "";
                if (c1.isChecked()) {
                    answer += c1.getText().toString() + " ";
                }
                if (c2.isChecked()) {
                    answer += c2.getText().toString() + " ";
                }
                if (c3.isChecked()) {
                    answer += c3.getText().toString() + " ";
                }
                Toast toast = Toast.makeText(EpOne_15.this, "选中" + answer, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
* */

@Sample(
    name = "07. CodeToCreateView",
    description = "利用 code 创建视图。",
    documentation = "",
    tags = ["A-Self_demos"],
)
@Suppress("DEPRECATION")
// 将同一个View line添加为ListView的头部和尾部，这可能会导致显示问题。
// 通常情况下，ListView的头部和尾部应该是不同的View。
class CodeToCreateView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 布局
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.setBackgroundColor(getResources().getColor(R.color.color5))

        // listview
        val listView = ListView(this)
        listView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        // 配置分割线
        val line = View.inflate(this, R.layout.ep1_19, null)

        // 设置头部线
        listView.addHeaderView(line)

        // 创建适配器
        val ad = ArrayAdapter.createFromResource(
            this,
            R.array.ep1_19_listview, android.R.layout.simple_list_item_checked
        )
        listView.adapter = ad

        // 设置尾部线
        val footerLine = View.inflate(this, R.layout.ep1_19, null)
        listView.addFooterView(footerLine)

        // 放进去
        linearLayout.addView(listView)

        // 设置内容视图
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val s = parent.getItemAtPosition(position).toString()
                Toast.makeText(this, s, LENGTH_SHORT).show()
            }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

/*
* Java
public class EpOne_19 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 布局
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        // listview
        ListView listView = new ListView(this);
        // 配置分割线
        View line = View.inflate(this, R.layout.ep1_19, null);
        // 设置头部线
        listView.addHeaderView(line);
        // 创建适配器
        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(this,
                R.array.ep1_19_listview, android.R.layout.simple_list_item_checked);
        listView.setAdapter(ad);
        listView.addFooterView(line);
        // 放进去
        linearLayout.addView(listView);
        // 设置内容视图
        setContentView(linearLayout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                Toast.makeText(EpOne_19.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
*/

@Sample(
    name = "08. ArrayAdapter",
    description = "适配器",
    documentation = "",
    tags = ["A-Self_demos"],
)
class ListDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_demo)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        var strings = arrayOf("测试 1")
        for (i in 2 until 50) {
            strings += "测试 $i"
        }
        val ad = ArrayAdapter(this, android.R.layout.simple_list_item_1, strings)

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = ad

        listView.setOnItemClickListener { parent, _, position, _ ->
            val s = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "选择了：$s", LENGTH_SHORT).show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

// 'ListActivity' is deprecated. Deprecated in Java
// 在kt里似乎避免不了，即使用了@Suppress("DEPRECATION")
/*
* Java
public class ep1_20 extends ListActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] strings = new String[]{"测试1", "测试2", "测试3"};
        ArrayAdapter<String> ad =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
        // 该方法相对简单，直接通过this.setListAdapter(ad);配置适配器即可
        this.setListAdapter(ad);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String s = l.getItemAtPosition(position).toString();
        Toast.makeText(this, "选择了：" + s, Toast.LENGTH_SHORT).show();
    }
}
*/

@Sample(
    name = "09. TimeGetDemo",
    description = "获取日期 & 时间",
    documentation = "",
    tags = ["A-Self_demos"],
)
class TimeGetDemo : AppCompatActivity() {
    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_21)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val datePicker = findViewById<DatePicker>(R.id.date)
        val timePicker = findViewById<TimePicker>(R.id.time)
        timePicker.setIs24HourView(true)
        val instance = Calendar.getInstance()
        year = instance[Calendar.YEAR]
        month = instance[Calendar.MONTH]
        day = instance[Calendar.DAY_OF_MONTH]
        hour = instance[Calendar.HOUR_OF_DAY]
        minute = instance[Calendar.MINUTE]
        datePicker.init(
            year,
            month,
            day
        ) { _, year, month, day ->
            this@TimeGetDemo.year = year
            this@TimeGetDemo.month = month
            this@TimeGetDemo.day = day
            show(year, month, day, hour, minute) // 通过消息框显示日期和时间
        }
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            this@TimeGetDemo.hour = hour
            month = minute
            Log.i("time", "时间：$hour$minute")
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    private fun show(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        val str = (year.toString() + "年" + (month + 1) + "月" + day + "日" + hour + ":"
            + minute) // 获取拾取器设置的日期和时间
        Toast.makeText(this, str, LENGTH_SHORT).show() // 显示消息提示框
    }
}

/*
* Java
public class ep1_21 extends Activity {

    int year;
    int month;
    int day;
    int hour;
    int minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_21);
        DatePicker datePicker = findViewById(R.id.date);
        TimePicker timePicker = findViewById(R.id.time);
        timePicker.setIs24HourView(true);
        Calendar instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH);
        day = instance.get(Calendar.DAY_OF_MONTH);
        hour = instance.get(Calendar.HOUR_OF_DAY);
        minute = instance.get(Calendar.MINUTE);
        datePicker.init(
                year,
                month,
                day,
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        ep1_21.this.year = year;
                        ep1_21.this.month = month;
                        ep1_21.this.day = day;
                        show(year, month, day, hour, minute); // 通过消息框显示日期和时间
                    }
                });
        timePicker.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hour, int minute) {
                        ep1_21.this.hour = hour;
                        ep1_21.this.month = minute;
                        Log.i("time", "时间：" + hour + minute);
                    }
                });
    }

    private void show(int year, int month, int day, int hour, int minute) {
        String str =
                year + "年" + (month + 1) + "月" + day + "日" + hour + ":"
                        + minute; // 获取拾取器设置的日期和时间
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show(); // 显示消息提示框
    }
}
*/

@Sample(
    name = "10. TimeGetDemoExt",
    description = "计时器",
    documentation = "",
    tags = ["A-Self_demos"],
)
class TimeGetDemoExt : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep1_22)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val chronometer = findViewById<Chronometer>(R.id.chro)
        // 设置起始时间
        chronometer.setBase(SystemClock.elapsedRealtime())
        // 设置格式
        chronometer.setFormat("已用时间:%s")
        val start = findViewById<Button>(R.id.startbutton)
        val end = findViewById<Button>(R.id.endbutton)
        val for0 = findViewById<Button>(R.id.for0)
        start.setOnClickListener { chronometer.start() }
        end.setOnClickListener { chronometer.stop() }
        for0.setOnClickListener { chronometer.setBase(SystemClock.elapsedRealtime()) }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

/*
* Java
public class ep1_22 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_22);
        Chronometer chronometer = findViewById(R.id.chro);
        //        设置起始时间
        chronometer.setBase(SystemClock.elapsedRealtime());
        //        设置格式
        chronometer.setFormat("已用时间:%s");
        Button start = findViewById(R.id.startbutton);
        Button end = findViewById(R.id.endbutton);
        Button for0 = findViewById(R.id.for0);
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chronometer.start();
                    }
                });
        end.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chronometer.stop();
                    }
                });
        for0.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    }
                });
    }
}
*/
