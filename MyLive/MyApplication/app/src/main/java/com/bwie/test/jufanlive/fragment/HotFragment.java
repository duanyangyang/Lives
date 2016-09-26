package com.bwie.test.jufanlive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.test.jufanlive.R;

/**
 * 创建者：段洋洋
 * 日期：2016/09/26
 * 简介：home中的热门信息fragment
 */
public class HotFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hot, container, false);
        return view;
    }
}
