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
 * 简介：主界面中的自己信息fragment
 */
public class MeInformationFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me_infomation, container, false);
        return view;
    }
}
