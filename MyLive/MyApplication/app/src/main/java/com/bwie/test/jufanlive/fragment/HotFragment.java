package com.bwie.test.jufanlive.fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bwie.test.jufanlive.R;
import com.bwie.test.jufanlive.adapter.HotLvAdapter;
import com.bwie.test.jufanlive.adapter.LoopAdapter;
import com.bwie.test.jufanlive.bean.HotData;
import com.bwie.test.jufanlive.net.HttpNet;
import com.bwie.test.jufanlive.net.ResponseListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：段洋洋
 * 日期：2016/09/26
 * 简介：home中的热门信息fragment
 */
public class HotFragment extends Fragment {
    private View view;
    private ListView hotLv;
    private ViewPager hotVp;
    private String address = "http://live.jufan.tv/cgi/hall/get?userid=500073885&type=hot&r=hkij&page=";
    private PullToRefreshScrollView hotSv;
    private boolean flag;
    private RadioGroup hotRg;
    private List<HotData.ContentBean.ListBean> list;
    private List<HotData.ContentBean.ListBean> lists;
    private int choose;
    private List<HotData.ContentBean.BannerBean> banner;
    private ArrayList<ImageView> imageList;
    private int i = 0;
    private String url;
    private HotLvAdapter lvAdapter;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // 翻页
            hotVp.setCurrentItem(hotVp.getCurrentItem() + 1);
            // 延迟调用
            sendMessageDelay();
        }

        ;
    };
    private int height;
    private int width;

    private void sendMessageDelay() {
        //延迟执行翻页
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hot, container, false);
        url = address + i;
        lists = new ArrayList<HotData.ContentBean.ListBean>();
        initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        return view;
    }

    private void initView() {
        hotLv = (ListView) view.findViewById(R.id.hot_lv);
        hotVp = (ViewPager) view.findViewById(R.id.hot_vp);
        hotSv = (PullToRefreshScrollView) view.findViewById(R.id.hot_sv);
        hotRg = (RadioGroup) view.findViewById(R.id.hot_rg);
        //设置ListView不聚焦
        hotLv.setFocusable(false);
        initViewPager();
        sendMessageDelay();
        hotSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                flag = true;
                i = 0;
                choose = 1;
                initViewPager();
                hotSv.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                flag = true;
                ++i;
                choose = 2;
                initViewPager();
                hotSv.onRefreshComplete();
            }
        });
        hotVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hotRg.check(position % banner.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPager() {
        HttpNet.getObjectMinongApi(url, new ResponseListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s) {
                HotData hotData = new Gson().fromJson(s.toString(), HotData.class);
                list = hotData.getContent().getList();
                banner = hotData.getContent().getBanner();
                if (list.size() == 0) {
                    Toast.makeText(getActivity(), "已加载全部", Toast.LENGTH_SHORT).show();
                } else {
                    if (flag) {
                        if (choose == 1) {
                            lists.clear();
                            lists.addAll(list);
                            lvAdapter.notifyDataSetChanged();
                        } else if (choose == 2) {
                            lists.addAll(list);
                            lvAdapter.notifyDataSetChanged();
                        }
                    } else {
                        //修改添加设置ViewPager的当前页，为了保证左右轮播
                        drawCricle();
                        hotVp.setAdapter(new LoopAdapter(banner, getContext()));
                        lists.addAll(list);
                        lvAdapter = new HotLvAdapter(lists, getActivity(), height, width);
                        hotLv.setAdapter(lvAdapter);
                    }

                }

            }
        });


    }


    private void drawCricle() {
        //左边距参数
        int wrap = RadioGroup.LayoutParams.WRAP_CONTENT;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(wrap, wrap);
        params.leftMargin = 8;
        for (int i = 0; i < banner.size(); i++) {
            RadioButton rb = new RadioButton(getActivity());
            rb.setId(i);
            //设置button属性
            rb.setButtonDrawable(R.drawable.x_dot_selector);
            //如果是第一个时，不需要加左边距
            if (i != 0) {
                hotRg.addView(rb, params);
            } else
                hotRg.addView(rb);
        }
        hotRg.check(0);
    }
}
