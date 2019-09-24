package com.study.androidnote.welcome.view;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.study.androidnote.welcome.R;
import com.study.androidnote.welcome.R2;
import com.study.androidnote.welcome.view.custom.FullScreenVideoView;
import com.study.biz.manager.JumpManager;
import com.study.commonlib.base.activity.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhao.qingyue on 2019/9/24.
 * 启动页——视频
 */
public class StartVideoActivity extends BaseActivity {

    private int COUNT_DOWN = 3;

    @BindView( R2.id.videoView)
    FullScreenVideoView videoView;

    @BindView( R2.id.tv_skip)
    TextView mSkip;

    private int mCountdown;

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity_start_video;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initVideo();
    }

    private void initVideo() {
//        String videoUrl = "";
//        videoView.setVideoURI(Uri.parse(videoUrl));
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.herologo;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(false);

                COUNT_DOWN = mp.getDuration() / 1000;
                mCountdown = COUNT_DOWN;
                updateSkipText(true);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 播放结束
                onJump();
            }
        });
    }

    @OnClick({R2.id.tv_skip })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_skip) {
            onJump();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onJump() {
        JumpManager.endOfWelcome();
        finish();
    }

    private void updateSkipText(boolean firsTime) {
        if (!firsTime) {
            mCountdown--;
        }
        String skipHint = getResources().getString(R.string.welcome_skip);
        String skip = String.format(skipHint, mCountdown);
        mSkip.setText(skip);
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @SuppressLint( "HandlerLeak" )
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    if (mCountdown > 0) {
                        updateSkipText(false);
                    } else {
                        onJump();
                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
