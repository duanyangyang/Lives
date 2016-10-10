package com.bwie.test.jufanlive.application;

import android.app.Activity;
import android.app.Application;

import com.bwie.test.jufanlive.net.VolleyUtil;
import com.umeng.socialize.PlatformConfig;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/9/25.
 */
public class MyApplication extends Application {

    public static LinkedList<Activity> mList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        //第三方登录初始化
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");

        VolleyUtil.initialize(getApplicationContext());
    }

    //创建Activity时添加到集合当中
    public static void addActivity(Activity activity) {
        if (mList.contains(activity)) {
            return;
        }
        mList.add(activity);
    }

    //销毁Activity时从集合中移除
    public static void removeActivity(Activity activity) {
        if (mList.contains(activity)) {
            mList.remove(activity);
        }
    }

    //一键退出
    public static void destoryAll() {
        for (Activity ac : mList) {
            ac.finish();
        }
        System.exit(0);
    }

}
