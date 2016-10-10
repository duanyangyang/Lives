package com.bwie.test.jufanlive.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bwie.test.jufanlive.R;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePlayActivity extends AppCompatActivity {
    private String videoURl;
    private TXCloudVideoView mPlayerView;
    private TXLivePlayer mLivePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);
        videoURl = getIntent().getStringExtra("vodUrl");
        Log.i("ligang",videoURl);
        //播放器控件
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        //创建播放器
        mLivePlayer = new TXLivePlayer(this);
        TXLivePlayer mTXLivePlayer = new TXLivePlayer(this);
        mTXLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.startPlay(videoURl, TXLivePlayer.PLAY_TYPE_VOD_MP4);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
        }
        if (mPlayerView != null) {
            mPlayerView.onDestroy();
        }
    }
}
