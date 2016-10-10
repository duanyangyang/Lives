package com.bwie.test.jufanlive.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bwie.test.jufanlive.R;
import com.bwie.test.jufanlive.activity.LivePlayActivity;
import com.bwie.test.jufanlive.bean.HotData;
import com.bwie.test.jufanlive.net.HttpNet;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */

public class HotLvAdapter extends BaseAdapter {

    private List<HotData.ContentBean.ListBean>  listBeen;
    private Context context;
    private int height;
    private int width;
    public HotLvAdapter(List<HotData.ContentBean.ListBean> listBeen, Context context, int height,int width) {
        this.height = height;
        this.listBeen = listBeen;
        this.context = context;
        this.width=width;
    }

    @Override
    public int getCount() {
        if (listBeen != null && listBeen.size() > 0) {
            return listBeen.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        ViewGroup.LayoutParams para;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.hot_lv_item, null);
            vh.rl = (RelativeLayout) convertView.findViewById(R.id.hot_lv_item_rl);
            vh.iv = (ImageView) convertView.findViewById(R.id.hot_lv_item_iv);
            vh.rl.setMinimumHeight(height / 2);
            para = vh.iv.getLayoutParams();
            para.height = height / 2;
            para.width = width;
            vh.iv.setLayoutParams(para);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final HotData.ContentBean.ListBean listBean = listBeen.get(position);
        HttpNet.getBitmapYuan(listBean.getBigheadimg(),vh.iv,context);
        vh.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LivePlayActivity.class);
                intent.putExtra("vodUrl",listBean.getVideo());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        RelativeLayout rl;
    }
}
