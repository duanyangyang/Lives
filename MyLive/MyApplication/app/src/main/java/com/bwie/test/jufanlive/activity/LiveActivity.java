package com.bwie.test.jufanlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bwie.test.jufanlive.R;

public class LiveActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton zhiboback;
    private EditText livezhiboedit;
    private ImageButton livezhibowechatuncheckedn;
    private ImageButton livezhibowechatcheckedp;
    private ImageButton livezhibofrienduncheckedn;
    private ImageButton livezhibofriendcheckedp;
    private ImageButton livezhiboweibouncheckedn;
    private ImageButton livezhiboweibocheckedp;
    private ImageButton livezhiboqqzoneuncheckedn;
    private ImageButton livezhiboqqzonecheckedp;
    private ImageButton livezhiboqquncheckedn;
    private ImageButton livezhiboqqcheckedp;
    private Button livezhibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initialize();
    }

    private void initialize() {
        zhiboback = (ImageButton) findViewById(R.id.zhibo_back);
        livezhiboedit = (EditText) findViewById(R.id.live_zhibo_edit);
        livezhibowechatuncheckedn = (ImageButton) findViewById(R.id.live_zhibo_wechat_unchecked_n);
        livezhibowechatcheckedp = (ImageButton) findViewById(R.id.live_zhibo_wechat_checked_p);
        livezhibofrienduncheckedn = (ImageButton) findViewById(R.id.live_zhibo_friend_unchecked_n);
        livezhibofriendcheckedp = (ImageButton) findViewById(R.id.live_zhibo_friend_checked_p);
        livezhiboweibouncheckedn = (ImageButton) findViewById(R.id.live_zhibo_weibo_unchecked_n);
        livezhiboweibocheckedp = (ImageButton) findViewById(R.id.live_zhibo_weibo_checked_p);
        livezhiboqqzoneuncheckedn = (ImageButton) findViewById(R.id.live_zhibo_qqzone_unchecked_n);
        livezhiboqqzonecheckedp = (ImageButton) findViewById(R.id.live_zhibo_qqzone_checked_p);
        livezhiboqquncheckedn = (ImageButton) findViewById(R.id.live_zhibo_qq_unchecked_n);
        livezhiboqqcheckedp = (ImageButton) findViewById(R.id.live_zhibo_qq_checked_p);
        livezhibo = (Button) findViewById(R.id.live_zhibo);

        livezhibowechatuncheckedn.setOnClickListener(this);
        livezhibowechatcheckedp.setOnClickListener(this);
        livezhibofrienduncheckedn.setOnClickListener(this);
        livezhibofriendcheckedp.setOnClickListener(this);
        livezhiboweibouncheckedn.setOnClickListener(this);
        livezhiboweibocheckedp.setOnClickListener(this);
        livezhiboqqzoneuncheckedn.setOnClickListener(this);
        livezhiboqqzonecheckedp.setOnClickListener(this);
        livezhiboqquncheckedn.setOnClickListener(this);
        livezhiboqqcheckedp.setOnClickListener(this);
        livezhibo.setOnClickListener(this);
        zhiboback.setOnClickListener(this);
        livezhibofriendcheckedp.setVisibility(View.VISIBLE);
        livezhibofrienduncheckedn.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.zhibo_back:
                Intent i = new Intent(LiveActivity.this, MainInterfaceActivity.class);
                startActivity(i);
                finish();
                break;
            //微信点击时
            case R.id.live_zhibo_wechat_unchecked_n:
                livezhibowechatuncheckedn.setVisibility(View.GONE);
                livezhibowechatcheckedp.setVisibility(View.VISIBLE);

                livezhibofriendcheckedp.setVisibility(View.GONE);
                livezhibofrienduncheckedn.setVisibility(View.VISIBLE);

                livezhiboweibocheckedp.setVisibility(View.GONE);
                livezhiboweibouncheckedn.setVisibility(View.VISIBLE);

                livezhiboqqzonecheckedp.setVisibility(View.GONE);
                livezhiboqquncheckedn.setVisibility(View.VISIBLE);

                livezhiboqquncheckedn.setVisibility(View.VISIBLE);
                livezhiboqqcheckedp.setVisibility(View.GONE);
                break;
            case R.id.live_zhibo_wechat_checked_p:

                break;
            //朋友圈点击时
            case R.id.live_zhibo_friend_unchecked_n:
                livezhibowechatuncheckedn.setVisibility(View.VISIBLE);
                livezhibowechatcheckedp.setVisibility(View.GONE);

                livezhibofriendcheckedp.setVisibility(View.VISIBLE);
                livezhibofrienduncheckedn.setVisibility(View.GONE);

                livezhiboweibocheckedp.setVisibility(View.GONE);
                livezhiboweibouncheckedn.setVisibility(View.VISIBLE);

                livezhiboqqzonecheckedp.setVisibility(View.GONE);
                livezhiboqquncheckedn.setVisibility(View.VISIBLE);

                livezhiboqquncheckedn.setVisibility(View.VISIBLE);
                livezhiboqqcheckedp.setVisibility(View.GONE);
                break;
            case R.id.live_zhibo_friend_checked_p:
                break;
            //微博点击时
            case R.id.live_zhibo_weibo_unchecked_n:
                livezhibowechatuncheckedn.setVisibility(View.VISIBLE);
                livezhibowechatcheckedp.setVisibility(View.GONE);

                livezhibofriendcheckedp.setVisibility(View.GONE);
                livezhibofrienduncheckedn.setVisibility(View.VISIBLE);

                livezhiboweibocheckedp.setVisibility(View.VISIBLE);
                livezhiboweibouncheckedn.setVisibility(View.GONE);

                livezhiboqqzonecheckedp.setVisibility(View.GONE);
                livezhiboqquncheckedn.setVisibility(View.VISIBLE);

                livezhiboqquncheckedn.setVisibility(View.VISIBLE);
                livezhiboqqcheckedp.setVisibility(View.GONE);
                break;
            case R.id.live_zhibo_weibo_checked_p:
                break;
            //QQ空间点击时
            case R.id.live_zhibo_qqzone_unchecked_n:
                break;
            case R.id.live_zhibo_qqzone_checked_p:
                break;
            //QQ点击时
            case R.id.live_zhibo_qq_unchecked_n:
                livezhibowechatuncheckedn.setVisibility(View.VISIBLE);
                livezhibowechatcheckedp.setVisibility(View.GONE);

                livezhibofriendcheckedp.setVisibility(View.GONE);
                livezhibofrienduncheckedn.setVisibility(View.VISIBLE);

                livezhiboweibocheckedp.setVisibility(View.GONE);
                livezhiboweibouncheckedn.setVisibility(View.VISIBLE);

                livezhiboqqzonecheckedp.setVisibility(View.GONE);
                livezhiboqquncheckedn.setVisibility(View.VISIBLE);

                livezhiboqquncheckedn.setVisibility(View.GONE);
                livezhiboqqcheckedp.setVisibility(View.VISIBLE);
                break;
            case R.id.live_zhibo_qq_checked_p:
                break;
            //推流点击时
            case R.id.live_zhibo:
                Toast.makeText(LiveActivity.this, "推流", Toast.LENGTH_SHORT).show();
                Intent pushIntent = new Intent(LiveActivity.this, PushFlowActivity.class);
                startActivity(pushIntent);
                break;
        }
    }
}
