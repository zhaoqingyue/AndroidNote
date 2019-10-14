package com.study.androidnote.welcome.view;

import android.media.MediaPlayer;
import com.study.androidnote.welcome.R;
import com.study.androidnote.welcome.R2;
import com.study.androidnote.welcome.view.custom.FullScreenVideoView;
import com.study.commonlib.base.fragment.LazyLoadFragment;
import com.study.commonlib.util.utilcode.LogUtils;
import butterknife.BindView;

public class GuidePagerFragment extends LazyLoadFragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    @BindView( R2.id.videoView)
    FullScreenVideoView videoView;

    private int curPage;
    private boolean mHasPaused;

    @Override
    protected int getLayoutResID() {
        return R.layout.welcome_fragment_guide_pager;
    }

    @Override
    protected void initData() {
        initVideo();
    }

    private void initVideo() {
        if (getArguments() == null)
            return;

        int videoRes = getArguments().getInt("res");
        curPage = getArguments().getInt("page");
        videoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + videoRes);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener( new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtils.e( "ZQY", "onError  " + what + ", " +  extra);
                return true;
            }
        } );
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (videoView != null) {
            videoView.requestFocus();
            videoView.seekTo(0);
            videoView.start();
            videoView.setOnCompletionListener(this);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 播放结束
        LogUtils.e( "ZQY", "onCompletion 000 curPage: " + curPage );
        ((GuideVideoActivity)getActivity()).next(curPage);
        LogUtils.e( "ZQY", "onCompletion 111 curPage: " + curPage );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHasPaused) {
            if (videoView != null) {
                videoView.seekTo(curPage);
                videoView.resume();
            }
        }
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView != null) {
            curPage = videoView.getCurrentPosition();
        }
        mHasPaused = true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
