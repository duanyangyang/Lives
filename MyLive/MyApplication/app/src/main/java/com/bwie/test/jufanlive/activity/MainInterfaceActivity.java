package com.bwie.test.jufanlive.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bwie.test.jufanlive.R;
import com.bwie.test.jufanlive.fragment.HomePageFragment;
import com.bwie.test.jufanlive.fragment.MeInformationFragment;

public class MainInterfaceActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton maininterfacehome;
    private RadioButton maininterfacemeinformation;
    private ImageView mainparty;
    private FragmentManager fragmentManager;
    private HomePageFragment hpf;
    private MeInformationFragment meInformationFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Toast.makeText(MainInterfaceActivity.this, "直播", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
