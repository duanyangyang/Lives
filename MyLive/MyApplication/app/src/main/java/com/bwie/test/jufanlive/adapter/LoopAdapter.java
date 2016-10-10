package com.bwie.test.jufanlive.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.test.jufanlive.bean.HotData;
import com.bwie.test.jufanlive.net.HttpNet;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
public class LoopAdapter extends PagerAdapter{
    private List<HotData.ContentBean.BannerBean>  mList;
    private Context context;
    public LoopAdapter(List<HotData.ContentBean.BannerBean> mList,Context context) {
        this.mList = mList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setTag(position);
        HttpNet.getBitmap(mList.get(position%mList.size()).getImg(), imageView, context);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
