package com.bwie.test.jufanlive.bean;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者： 段洋洋
 * 创建时间： 2016-09-27.
 * 描述：重写ListView
 */
public class HpListView extends ListView {
    public HpListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HpListView(Context context) {
        super(context);
    }

    public HpListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //重写该方法后，导致listView无法滑动
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
