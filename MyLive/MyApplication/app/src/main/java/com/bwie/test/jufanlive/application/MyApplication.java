package com.bwie.test.jufanlive.application;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/9/25.
 */
public class MyApplication extends Application {

    public static LinkedList<Activity> mList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

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
