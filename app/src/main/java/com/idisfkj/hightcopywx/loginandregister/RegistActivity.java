package com.idisfkj.hightcopywx.loginandregister;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.loginandregister.entity.User;
import com.idisfkj.hightcopywx.util.OKHttpUtil;
import com.idisfkj.hightcopywx.util.ResultCode;

import org.json.JSONException;
import org.json.JSONObject;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class RegistActivity extends Activity {

    private EditText mUsername;                //用户名
    private EditText mPassword;                //密码
    private EditText mRrepassword;             //确认密码
    private EditText mRealName;                    //真实姓名
    private EditText mCompanyId;                    //企业编号
    private ImageView register_return;
    private User userinfo=new User(); //用户信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        mUsername = (EditText) findViewById(R.id.userName_register);//查找用户名控件
        mPassword = (EditText) findViewById(R.id.password_register);   //查找密码控件
        mRrepassword=(EditText) findViewById(R.id.repassword_register);  //重复密码控件
        mRealName=(EditText) findViewById(R.id.realname_register); //真实姓名控件
        mCompanyId=(EditText) findViewById(R.id.companyId_register); //企业编号控件
        register_return=(ImageView)findViewById(R.id.register_return);//返回


        //注册按钮
        Button mEmailSignInButton = (Button) findViewById(R.id.register_submit_btn);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();//调用函数检查登陆信息是否合法

            }
        });

        register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    /**
     * 输入的检查
     */
    private void attemptLogin() {

        // 初始化控件错误信息
        mUsername.setError(null);//用户名
        mRealName.setError(null);//真实姓名
        mPassword.setError(null);//密码
        mRrepassword.setError(null);//确认密码
        mCompanyId.setError(null);//企业编号
        // 获取输入信息.
        String strUsername = mUsername.getText().toString();
        String strPassword = mPassword.getText().toString();
        String strRrepassword = mRrepassword.getText().toString();
        String strRealName=mRealName.getText().toString();
        String strCompanyId=mCompanyId.getText().toString();

        userinfo.setUsername(strUsername);
        userinfo.setPassword(strPassword);
        userinfo.setName(strRealName);
        userinfo.setCompanyId(strCompanyId);

        boolean cancel = false;
        View focusView = null;
        // 检查用户名
        if ( TextUtils.isEmpty(strUsername) ) {
            mUsername.setError("用户名不能为空！");
            focusView = mUsername;
            cancel = true;
        }
        // 检查密码是否为空
        if ( TextUtils.isEmpty(strUsername) ) {
            mPassword.setError("密码不能为空！");
            focusView = mPassword;
            cancel = true;
        }
        // 检查确认密码是否为空
        if ( TextUtils.isEmpty(strRrepassword) ) {
            mRrepassword.setError("确认密码不能为空！");
            focusView = mRrepassword;
            cancel = true;
        }

        // 检查密码是否有效
        if ( !TextUtils.isEmpty(strPassword) && !isPasswordValid(strPassword) ) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if(!strRrepassword.equals(strPassword)){
            mRrepassword.setError("两次密码不一致");
            focusView = mRrepassword;
            cancel = true;
        }

        //检查真实姓名
        if(TextUtils.isEmpty(strRealName)){
            mRealName.setError("真实姓名不能为空");
            focusView = mRealName;
            cancel = true;
        }

        //检查公司编号
        if(TextUtils.isEmpty(strCompanyId)){
            mCompanyId.setError("企业编号不能为空");
            focusView = mCompanyId;
            cancel = true;
        }
        if ( cancel ) {
            focusView.requestFocus();
        } else {
           // //验证用户名是否存在
//            new UserIsExistedThread().start();
            OKHttpUtil http = new OKHttpUtil(RegistActivity.this);
            http.insertUserInfo(userinfo, getProperties(getApplicationContext()).getProperty("MAIN_REGIST_REGIST"));
        }
    }


    /**
     * 密码是否和非法，至少需要4位
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    //用户名是否存在的请求--------开始-----------------
    Handler userIsExistedHandlerlist = new Handler() {
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
                if(result.equals(ResultCode.RESULT_CODE_ERROR)){
                    mUsername.setText("");
                    mUsername.setError("该用户名已存在,请重新输入！");
                    T("用户名已存在,请重新输入！");
                }else if(result.equals(ResultCode.RESULT_CODE_TIMEOUT)){
                    T("注册超时");
                }else if(result.equals(ResultCode.RESULT_CODE_EXCEPTION)){
                    T("系统异常");
                }else{
                    OKHttpUtil http = new OKHttpUtil(RegistActivity.this);
                    http.insertUserInfo(userinfo, getProperties(getApplicationContext()).getProperty("MAIN_REGIST_REGIST"));
                }
                //后添加----结束
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    public class UserIsExistedThread extends Thread {

        //继承Thread类，并改写其run方法
        public void run() {
            final String address = getProperties(getApplicationContext()).getProperty("USER_IS_EXISTED") + "?username=" + mUsername.getText();
            // 需要做的事:发送消息
            OKHttpUtil http = new OKHttpUtil(RegistActivity.this);
            String str = http.httpGet(address);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", str);
            msg.setData(data);
            userIsExistedHandlerlist.sendMessage(msg);
        }
    }

    public void T(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    //用户名是否存在的请求------------结束----------------

}
