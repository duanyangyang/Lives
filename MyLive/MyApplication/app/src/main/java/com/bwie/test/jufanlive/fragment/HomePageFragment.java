package com.bwie.test.jufanlive.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.jufanlive.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：段洋洋
 * 日期：2016/09/26
 * 简介：主界面中的首页fragment
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {
    private ViewPager mViewPager;
    private View view;
    private ImageView homesearch;
    private ImageView homerichlist;
    private TextView homefollow;
    private TextView homehot;
    private TextView homenewest;
    private ViewPager homeviewpager;
    private List<Fragment> mFragmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        initialize();
        return view;
    }

    private void initialize() {
        mFragmentList = new ArrayList<>();
        homesearch = (ImageView) view.findViewById(R.id.home_search);
        homerichlist = (ImageView) view.findViewById(R.id.home_richlist);
        homefollow = (TextView) view.findViewById(R.id.home_follow);
        homehot = (TextView) view.findViewById(R.id.home_hot);
        homenewest = (TextView) view.findViewById(R.id.home_newest);
        homeviewpager = (ViewPager) view.findViewById(R.id.home_viewpager);
        homesearch.setOnClickListener(this);
        homerichlist.setOnClickListener(this);
        homefollow.setOnClickListener(this);
        homehot.setOnClickListener(this);
        homenewest.setOnClickListener(this);

        NewetsFragment newetsFragment = new NewetsFragment();
        HotFragment hotFragment = new HotFragment();
        FollowFragment followFragment = new FollowFragment();

        mFragmentList.add(followFragment);
        mFragmentList.add(hotFragment);
        mFragmentList.add(newetsFragment);

        homeviewpager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        homeviewpager.setCurrentItem(1);

        homeviewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    homefollow.setTextColor(Color.RED);
                    homefollow.setTextSize(23);
                    homehot.setTextSize(16);
                    homenewest.setTextSize(16);
                    homehot.setTextColor(Color.BLACK);
                    homenewest.setTextColor(Color.BLACK);
                } else if (position == 1) {
                    homefollow.setTextSize(16);
                    homehot.setTextSize(23);
                    homenewest.setTextSize(16);
                    homefollow.setTextColor(Color.BLACK);
                    homehot.setTextColor(Color.RED);
                    homenewest.setTextColor(Color.BLACK);
                } else if (position == 2) {
                    homefollow.setTextSize(16);
                    homehot.setTextSize(16);
                    homenewest.setTextSize(23);
                    homefollow.setTextColor(Color.BLACK);
                    homehot.setTextColor(Color.BLACK);
                    homenewest.setTextColor(Color.RED);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //搜索
            case R.id.home_search:
                Toast.makeText(getActivity(), "搜索", Toast.LENGTH_SHORT).show();
                break;
            //奖励
            case R.id.home_richlist:
                Toast.makeText(getActivity(), "奖励", Toast.LENGTH_SHORT).show();
                break;
            //最热
            case R.id.home_hot:
                homeviewpager.setCurrentItem(1);
                break;
            //最新
            case R.id.home_newest:
                homeviewpager.setCurrentItem(2);
                break;
            //关注
            case R.id.home_follow:
                homeviewpager.setCurrentItem(0);
                break;
        }
    }
}
