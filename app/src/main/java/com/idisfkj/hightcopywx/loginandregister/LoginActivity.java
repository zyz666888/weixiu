package com.idisfkj.hightcopywx.loginandregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.SettingActivity;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.test.TestHttpUtil;
import com.idisfkj.hightcopywx.util.OKHttpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

/**
 * 登录页面
 */
public class LoginActivity extends Activity {

    private static boolean isExit = false;//判断是否直接退出程序

    private EditText mUserName;  //用户名
    private EditText mPasswordView;  //密码
    boolean canlogin = false;

    /** 请求响应状态码-请求成功 */
    public final static String RESULT_CODE_OK = "01";
    /** 请求响应状态码-校验失败 */
    public final static String RESULT_CODE_ERROR = "02";
    /** 请求响应状态码-登录超时 */
    public final static String RESULT_CODE_TIMEOUT = "03";
    /** 请求响应状态码-系统异常 */
    public final static String RESULT_CODE_EXCEPTION = "04";

    public  ImageView settingbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        Intent intent=getIntent();
        String toast=intent.getStringExtra("toast");
        if(toast!=null&&!toast.isEmpty()) {
            Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_SHORT).show();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mUserName = (EditText) findViewById(R.id.userName_login);   //用户名控件
        mPasswordView = (EditText) findViewById(R.id.password_login);         //密码控件
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();//调用函数检查登陆信息是否合法
                    return true;
                }
                return false;
            }
        });

        //登录按钮
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();//调用函数检查登陆信息是否合法
            }
        });

        //注册按钮
        Button registButton = (Button) findViewById(R.id.register_login);
        registButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegistActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        //设置按钮
        settingbtn= (ImageView) findViewById(R.id.settings);
        settingbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//点击之后跳转到服务器设置页面
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SettingActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    //退出确认
    //@Override
    //public boolean onKeyDown(int keyCode, KeyEvent event) {
    //    if(keyCode==KeyEvent.KEYCODE_BACK){
    //        if(!isExit){
    //            isExit=true;
    //            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
    //            handler.sendEmptyMessageDelayed(0,2000);
    //        }
    //    }else {
    //        finish();
    //        System.exit(0);
    //    }
    //    return super.onKeyDown(keyCode, event);
    //}

    /**
     * 输入信息的检查
     */
    private void attemptLogin() {

        // 初始化错误信息为null
        mUserName.setError(null);
        mPasswordView.setError(null);

        // 获取输入信息.
        String email = mUserName.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;//是否是非法信息
        View focusView = null;

        // 检查密码是否有效
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        //  检查邮箱
        if (TextUtils.isEmpty(email)) {
            mUserName.setError(getString(R.string.error_field_required));
            focusView = mUserName;
            cancel = true;
        }

        if (cancel) {//非法信息
            focusView.requestFocus();//标签用于指定屏幕内的焦点View。
        } else {//合法信息
            checkCanLogin();
            if(canlogin)
            {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                new MyThread().start();
            }
        }
    }

    /**
     * 密码是否合法：至少需要4位
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }


    //登录的请求----------开始-----------------
    Handler handlerlist = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String val = data.getString("value");
            JSONObject dataJson = null;
            try {
                dataJson = new JSONObject(val);

                //产品名称
                String result = (String) dataJson.get("resultCode");
                if(result.equals(RESULT_CODE_OK)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if(result.equals(RESULT_CODE_ERROR)){
                    T("登录失败");
                }else if(result.equals(RESULT_CODE_TIMEOUT)){
                    T("登录超时");
                }else if(result.equals(RESULT_CODE_EXCEPTION)){
                    T("系统异常");
                }


                //后添加----结束

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    public class MyThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {
            final String address = getProperties(getApplicationContext()).getProperty("MAIN_LOGIN_LOGIN") + "?username=" + mUserName.getText() + "&password=" + mPasswordView.getText();
            // 需要做的事:发送消息
            OKHttpUtil http = new OKHttpUtil(LoginActivity.this);
            String str = http.httpGet(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            handlerlist.sendMessage(msg);
        }
    }

    public void T(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    //登陆跳转逻辑

    public void checkCanLogin ()
    {
        if (!mUserName.getText().toString().equals("") && !mPasswordView.getText().toString().equals("")) {
            canlogin = true;
        } else {
            canlogin = false;
            T("用户名和密码不能为空");
        }
    }
    //登录的请求----------结束----------------

}

