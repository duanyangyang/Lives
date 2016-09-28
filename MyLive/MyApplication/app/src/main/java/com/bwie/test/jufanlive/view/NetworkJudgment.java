package com.bwie.test.jufanlive.view;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 创建者：段洋洋
 * 日期：2016/09/28
 * 简介：网络判断的封装类
 */
public class NetworkJudgment {

    private static NetworkJudgment sNetworkJudgment;

    private NetworkJudgment() {
    }

    public static synchronized NetworkJudgment getIntence(Context mContext) {
        if (sNetworkJudgment == null) {
            sNetworkJudgment = new NetworkJudgment();
        }
        //获取连接管理器
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        //获取当前网络信息对象
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            //网络是否已经连接
            boolean b = activeNetworkInfo.isAvailable();
            //网络是否连好并可以传递数据
            boolean connected = activeNetworkInfo.isConnected();
            if (connected && b) {
                int type = activeNetworkInfo.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    Toast.makeText(mContext, "当前网络为WiFi", Toast.LENGTH_SHORT).show();
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    Toast.makeText(mContext, "当前网络为移动网络", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "网络不可用", Toast.LENGTH_SHORT).show();
        }
        return sNetworkJudgment;
    }

}
