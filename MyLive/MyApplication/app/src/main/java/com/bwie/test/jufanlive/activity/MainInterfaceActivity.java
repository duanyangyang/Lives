package com.bwie.test.jufanlive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bwie.test.jufanlive.R;
import com.bwie.test.jufanlive.application.MyApplication;
import com.bwie.test.jufanlive.fragment.HomePageFragment;
import com.bwie.test.jufanlive.fragment.MeInformationFragment;
import com.bwie.test.jufanlive.view.NetworkJudgment;

public class MainInterfaceActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton maininterfacehome;
    private RadioButton maininterfacemeinformation;
    private ImageView mainparty;
    private FragmentManager fragmentManager;
    private HomePageFragment hpf;
    private MeInformationFragment meInformationFragment;
    private FragmentTransaction fragmentTransaction;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        NetworkJudgment.getIntence(this);
        setContentView(R.layout.activity_main_interface);
        initialize();
    }

    private void initialize() {
        maininterfacehome = (RadioButton) findViewById(R.id.main_interface_home);
        maininterfacemeinformation = (RadioButton) findViewById(R.id.main_interface_me_information);
        mainparty = (ImageView) findViewById(R.id.main_party);
        maininterfacehome.setOnClickListener(this);
        maininterfacemeinformation.setOnClickListener(this);
        mainparty.setOnClickListener(this);
        //管理
        fragmentManager = getSupportFragmentManager();
        //事务
        fragmentTransaction = fragmentManager.beginTransaction();
        //创建fragment
        hpf = new HomePageFragment();
        meInformationFragment = new MeInformationFragment();
        fragmentTransaction.add(R.id.main_fl, hpf, "hpf1");
        fragmentTransaction.add(R.id.main_fl, meInformationFragment, "meInformationFragment1");
        fragmentTransaction.commit();
        //初始化数据
        switctFrag("hpf1");
    }

    private void switctFrag(String str) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if ("hpf1".equals(str)) {
            transaction.show(hpf);
            transaction.hide(meInformationFragment);
            maininterfacehome.setChecked(true);
            maininterfacemeinformation.setChecked(false);
        } else if ("meInformationFragment1".equals(str)) {
            transaction.hide(hpf);
            transaction.show(meInformationFragment);
            maininterfacehome.setChecked(false);
            maininterfacemeinformation.setChecked(true);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //显示首页
            case R.id.main_interface_home:
                switctFrag("hpf1");
                break;
            //显示个人信息
            case R.id.main_interface_me_information:
                switctFrag("meInformationFragment1");
                break;
            //跳转至直播
            case R.id.main_party:
                Intent i = new Intent(MainInterfaceActivity.this,LiveActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println(System.currentTimeMillis());
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // Toast.makeText(MainActivity.this, "再按一次退出程序", 0).show();
                exitTime = System.currentTimeMillis();
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确定要退出吗？");
                builder.setPositiveButton("退出",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // 一键退出
                                MyApplication.destoryAll();
                            }
                        });
                builder.setNegativeButton("再看看",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }
}
