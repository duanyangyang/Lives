package com.bwie.test.jufanlive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bwie.test.jufanlive.R;

import java.util.ArrayList;
import java.util.List;

public class FirstLoginActivity extends BaseActivity {

    private ViewPager mVp;
    private List<ImageView> ivList;
    private Button mBtn;
    private int [] s={R.mipmap.li_img_guide_1, R.mipmap.li_img_guide_2, R.mipmap.li_img_guide_3};
    private SharedPreferences mSf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSf=getSharedPreferences("jufan",MODE_PRIVATE);
        if(mSf.getBoolean("flag",false)){
            Intent mIntent=new Intent(FirstLoginActivity.this,LogoActivity.class);
            startActivity(mIntent);
            finish();
        }else{
            setContentView(R.layout.activity_first_login);
            SharedPreferences.Editor ed=mSf.edit();
            ed.putBoolean("flag",true);
            ed.commit();
            //找控件
            initView();
        }
    }

    private void initView() {
        mVp= (ViewPager) findViewById(R.id.vp_firstlogin);
        mBtn= (Button) findViewById(R.id.btn_comein);
        initData();
        MyVpAdapter mav=new MyVpAdapter();
        mVp.setAdapter(mav);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==ivList.size()-1){
                    mBtn.setVisibility(View.VISIBLE);
                }else{
                    mBtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(FirstLoginActivity.this,LogoActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
    }

    private void initData() {
        ivList=new ArrayList<ImageView>();
        for(int i=0;i<s.length;i++){
            ImageView iv=new ImageView(this);
            iv.setId(i);
            iv.setImageResource(s[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ivList.add(iv);
        }
    }
    class  MyVpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ivList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = ivList.get(position);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

}
