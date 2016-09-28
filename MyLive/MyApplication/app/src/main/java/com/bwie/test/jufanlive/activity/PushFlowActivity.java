package com.bwie.test.jufanlive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bwie.test.jufanlive.R;
import com.bwie.test.jufanlive.application.MyApplication;
import com.bwie.test.jufanlive.view.HWSupportList;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class PushFlowActivity extends AppCompatActivity implements ITXLivePushListener {


    private TXCloudVideoView videoview;
    private Button btnPlay;
    private Button btnCameraChange;
    private Button btnHWEncode;
    private Button btnFaceBeauty;
    private Button btnFlash;
    public boolean mVideoPublish;
    private TXLivePusher mLivePusher;
    private TXLivePushConfig mLivePushConfig;
    private boolean mFrontCamera = true;
    private boolean mHWVideoEncode = false;
    StringBuffer mLogMsg = new StringBuffer("");
    private boolean mHWListConfirmDialogResult = false;
    private boolean mFlashTurnOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        setContentView(R.layout.activity_push_flow);

        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();

        initialize();
    }

    private void initialize() {
        videoview = (TXCloudVideoView) findViewById(R.id.video_view);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnCameraChange = (Button) findViewById(R.id.btnCameraChange);
        btnHWEncode = (Button) findViewById(R.id.btnHWEncode);
        btnFaceBeauty = (Button) findViewById(R.id.btnFaceBeauty);
        btnFlash = (Button) findViewById(R.id.btnFlash);

        //播放部分
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoPublish) {
                    stopPublishRtmp();
                    mVideoPublish = false;
                } else {
                    FixOrAdjustBitrate();  //根据设置确定是“固定”还是“自动”码率
                    mVideoPublish = startPublishRtmp();
                }
            }
        });
        //摄像头的转换
        btnCameraChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrontCamera = !mFrontCamera;
                if (mLivePusher.isPushing()) {
                    mLivePusher.switchCamera();
                } else {
                    mLivePushConfig.setFrontCamera(mFrontCamera);
                }
                btnCameraChange.setBackgroundResource(mFrontCamera ? R.drawable.camera_change : R.drawable.camera_change2);
            }
        });
        //开启硬件加速
        btnHWEncode.getBackground().setAlpha(mHWVideoEncode ? 255 : 100);
        btnHWEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean HWVideoEncode = mHWVideoEncode;
                mHWVideoEncode = !mHWVideoEncode;
                btnHWEncode.getBackground().setAlpha(mHWVideoEncode ? 255 : 100);

                if (mHWVideoEncode) {
                    if (mLivePushConfig != null) {
                        if (Build.VERSION.SDK_INT < 16) {
                            Toast.makeText(PushFlowActivity.this, "硬件加速失败，当前手机API级别过低（最低16）", Toast.LENGTH_SHORT).show();
                            mHWVideoEncode = false;
                        } else {
                            if (HWSupportList.isHWVideoEncodeSupport() == false) {
                                HWListConfirmDialog();
                                if (mHWListConfirmDialogResult) {
                                    Toast.makeText(PushFlowActivity.this, "尝试硬件编码，推流端/播放端画面可能异常！", Toast.LENGTH_SHORT).show();
                                } else {
                                    mHWVideoEncode = false;
                                    Toast.makeText(PushFlowActivity.this, "取消硬件加速", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                if (HWVideoEncode != mHWVideoEncode) {
                    mLivePushConfig.setHardwareAcceleration(mHWVideoEncode);
                    if (mHWVideoEncode == false) {
                        Toast.makeText(PushFlowActivity.this, "取消硬件加速", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PushFlowActivity.this, "开启硬件加速", Toast.LENGTH_SHORT).show();
                    }
                }
                if (mLivePusher != null) {
                    mLivePusher.setConfig(mLivePushConfig);
                }
            }
        });
        //闪关灯部分
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLivePusher == null) {
                    return;
                }
                mFlashTurnOn = !mFlashTurnOn;
                if (!mLivePusher.turnOnFlashLight(mFlashTurnOn)) {
                    Toast.makeText(PushFlowActivity.this,
                            "打开闪光灯失败（1）大部分前置摄像头并不支持闪光灯（2）该接口需要在启动预览之后调用", Toast.LENGTH_SHORT).show();
                }
                btnFlash.setBackgroundResource(mFlashTurnOn ? R.drawable.flash_off : R.drawable.flash_on);
            }
        });

    }

    protected void HWListConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PushFlowActivity.this);
        builder.setMessage("警告：当前机型不在白名单中,是否继续尝试硬编码？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHWListConfirmDialogResult = true;
                throw new RuntimeException();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHWListConfirmDialogResult = false;
                throw new RuntimeException();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
        try {
            Looper.loop();
        } catch (Exception e) {
        }
    }

    public void FixOrAdjustBitrate() {
        if (mLivePushConfig == null || mLivePusher == null) {
            return;
        }
//        RadioButton rb = (RadioButton) getActivity().findViewById(mRadioGroupBitrate.getCheckedRadioButtonId());
//        int mode = Integer.parseInt((String) rb.getTag());

        switch (4) {
            case 4: /*720p*/
                if (mLivePusher != null) {
                    mLivePushConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280);
                    mLivePushConfig.setAutoAdjustBitrate(false);
                    mLivePushConfig.setVideoBitrate(1500);
                    mLivePusher.setConfig(mLivePushConfig);
                }
//                mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
                break;
            default:
                break;
        }
    }

    private void stopPublishRtmp() {
        mLivePusher.stopCameraPreview(true);
        mLivePusher.setPushListener(null);
        mLivePusher.stopPusher();
        videoview.setVisibility(View.GONE);

//        enableQRCodeBtn(true);
        btnPlay.setBackgroundResource(R.drawable.play_start);
    }

    private boolean startPublishRtmp() {
        String rtmpUrl = "rtmp://2000.livepush.myqcloud.com/live/2000_1f4652b179af11e69776e435c87f075e?bizid=2000";
        if (TextUtils.isEmpty(rtmpUrl) || (!rtmpUrl.trim().toLowerCase().startsWith("rtmp://"))) {
            mVideoPublish = false;
            Toast.makeText(PushFlowActivity.this, "推流地址不合法，目前支持rtmp推流!", Toast.LENGTH_SHORT).show();
            return false;
        }

        videoview.setVisibility(View.VISIBLE);
//        mLivePushConfig.setWatermark(mBitmap, 0, 0);
//        mLivePushConfig.setWatermark(mBitmap, 10, 10);

        int customModeType = 0;

        mLivePushConfig.setCustomModeType(customModeType);

        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setPushListener(this);
        mLivePusher.startCameraPreview(videoview);
        mLivePusher.startPusher(rtmpUrl.trim());
        mLivePusher.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);

//        enableQRCodeBtn(false);
        clearLog();
        int[] ver = TXLivePusher.getSDKVersion();
        if (ver != null && ver.length >= 3) {
            mLogMsg.append(String.format("rtmp sdk version:%d.%d.%d ", ver[0], ver[1], ver[2]));
//            mLogViewEvent.setText(mLogMsg);
        }

        btnPlay.setBackgroundResource(R.drawable.play_pause);

        // appendEventLog(0, "点击推流按钮！");

        return true;
    }

    protected void clearLog() {
        mLogMsg.setLength(0);
//        mLogViewEvent.setText("");
//        mLogViewStatus.setText("");
    }


    @Override
    public void onResume() {
        super.onResume();

        if (videoview != null) {
            videoview.onResume();
        }

        if (mVideoPublish && !mLivePusher.isPushing()) {
            mLivePusher.startCameraPreview(videoview);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (videoview != null) {
            videoview.onPause();
        }

        mLivePusher.stopCameraPreview(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPublishRtmp();
        if (videoview != null) {
            videoview.onDestroy();
        }
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        //appendEventLog(event, msg);
//        if (mScrollView.getVisibility() == View.VISIBLE){
//            mLogViewEvent.setText(mLogMsg);
//            scroll2Bottom(mScrollView, mLogViewEvent);
//        }
//        if (mLivePusher != null) {
//            mLivePusher.onLogRecord("[event:" + event + "]" + msg + "\n");
//        }
        //错误还是要明确的报一下
        if (event < 0) {
            Toast.makeText(PushFlowActivity.this, param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT) {
            stopPublishRtmp();
            mVideoPublish = false;
        } else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
            Toast.makeText(PushFlowActivity.this, param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
            mHWVideoEncode = false;
            mLivePushConfig.setHardwareAcceleration(mHWVideoEncode);
            // mBtnHWEncode.setBackgroundResource(mHWVideoEncode ? R.drawable.quick : R.drawable.quick2);
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
//        String str = getNetStatusString(status);
//        mLogViewStatus.setText(str);
//        Log.d(TAG, "Current status: " + status.toString());
//        if (mLivePusher != null){
//            mLivePusher.onLogRecord("[net state]:\n"+str+"\n");
//        }
    }
}
