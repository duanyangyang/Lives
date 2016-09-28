package com.bwie.test.jufanlive.bean;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 创建者：段洋洋
 * 日期：2016/09/27
 * 简介：自定义Gridview
 */
public class HpGridView extends GridView {
    public HpGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public HpGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public HpGridView(Context context) {
        super(context);
    }
    //重写该方法后，导致GridView无法滑动
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
