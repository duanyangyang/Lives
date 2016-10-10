package com.bwie.test.jufanlive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bwie.test.jufanlive.R;
import com.bwie.test.jufanlive.application.MyApplication;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private RadioButton mRb1;
    private RadioButton mRb2;
    private RadioButton mRb3;
    private RadioButton mRb4;
    private UMShareAPI mShareAPI;
    private SharedPreferences mSf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        mSf=getSharedPreferences("ligang",MODE_PRIVATE);
        if(mSf.getBoolean("isLogin",false)){
            Intent mInten=new Intent(LoginActivity.this, MainInterfaceActivity.class);
            startActivity(mInten);
            finish();
        }else{
            setContentView(R.layout.activity_login);
            initVeiw();
        }
    }

    private void initVeiw() {
        mRb1 = (RadioButton) findViewById(R.id.rb1);
        mRb2 = (RadioButton) findViewById(R.id.rb2);
        mRb3 = (RadioButton) findViewById(R.id.rb3);
        mRb4 = (RadioButton) findViewById(R.id.rb4);
        mRb1.setOnClickListener(this);
        mRb2.setOnClickListener(this);
        mRb3.setOnClickListener(this);
        mRb4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb1:
                Toast.makeText(LoginActivity.this, "微信登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb2:
                qqLogin();
                break;
            case R.id.rb3:
                Toast.makeText(LoginActivity.this, "手机登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb4:
                Toast.makeText(LoginActivity.this, "微博登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    public void qqLogin() {
        // 获取UMShareAPI
        mShareAPI = UMShareAPI.get(LoginActivity.this);
        // 授权平台
        SHARE_MEDIA platform = SHARE_MEDIA.QQ;
        mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {

                @Override
                public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {

                }

                @Override
                public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
                    // 获得头像
                    String mInage = arg2.get("profile_image_url");
                    // 获得昵称
                    String name = arg2.get("screen_name");
                    SharedPreferences.Editor ed=mSf.edit();
                    ed.putBoolean("isLogin",true);
                    ed.putString("QQname",name);
                    ed.putString("QQUrlImage",mInage);
                    ed.commit();
                    Intent mInten=new Intent(LoginActivity.this, MainInterfaceActivity.class);
                    startActivity(mInten);
                    finish();
                }

                @Override
                public void onCancel(SHARE_MEDIA arg0, int arg1) {

                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
