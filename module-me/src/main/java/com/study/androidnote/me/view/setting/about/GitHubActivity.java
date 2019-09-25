package com.study.androidnote.me.view.setting.about;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.ProgressBarLoading;
import com.study.commonlib.util.utilcode.NetworkUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目主页
 */
public class GitHubActivity extends BaseTopBarActivity {

    @BindView(R2.id.webview)
    WebView mWebView;

    @BindView( R2.id.pb_progress)
    ProgressBarLoading progressBar;

    @BindView( R2.id.tv_badnet)
    TextView badNet;

    private boolean isContinue = false;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_github;
    }

    protected boolean isHasWebView() {
        return true;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initWebSetting();
    }

    private void initWebSetting() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(false);
        //正常网络流程
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 进度条是隐藏状态，则显示
                if (View.INVISIBLE == progressBar.getVisibility()) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                // 大于80的进度，放慢速度加载，否则正常加载
                if (newProgress >= 80) {
                    // 拦截webView自己的处理方式
                    if (isContinue) {
                        return;
                    }

                    // 正常加载结束
                    progressBar.setCurProgress(100, 2000, new ProgressBarLoading.OnEndListener() {

                        @Override
                        public void onEnd() {
                            finishOperation(true, false);
                            isContinue = false;
                        }
                    });
                    isContinue = true;
                } else {
                    progressBar.setNormalProgress(newProgress);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            // https的处理方式
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            // 错误页面的逻辑处理
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                errorOperation();
                if (View.INVISIBLE == progressBar.getVisibility()) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                finishOperation(false, false);
            }
        });
        startLoadUrl();
    }

    private void startLoadUrl() {
        // 没有网络直接跳出方法
        if (!NetworkUtils.isConnected()) {
            finishOperation(false, true);
            return;
        }
        // 开始加载
        String h5Url = "https://github.com/zhaoqingyue/AndroidNote";
        mWebView.loadUrl(h5Url);
    }

    /**
     * 结束进行的操作
     */
    private void finishOperation(boolean normalEnd, final boolean netError) {
        // 最后加载设置100进度
        progressBar.setNormalProgress(100);
        if (normalEnd) {
            // 显示网络异常布局
            badNet.setVisibility(View.GONE);
            if (mWebView != null) {
                mWebView.setVisibility(View.VISIBLE);
            }
        } else {
            if (mWebView != null) {
                mWebView.setVisibility(View.GONE);
            }
            badNet.setVisibility(View.VISIBLE);
            if (!NetworkUtils.isConnected()) {
                badNet.setText(getString(R.string.toast_network_unavailable));
                return;
            } else {
                badNet.setText(getString(R.string.touch_the_screen_to_reload));
            }
        }
        // 点击重新连接网络
        badNet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                badNet.setVisibility(View.GONE);
                if (netError) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startLoadUrl();
                        }
                    }, 500);
                } else {
                    mWebView.clearCache(true);
                    // 重新加载网页
                    if (mWebView != null) {
                        mWebView.reload();
                        mWebView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        hideProgressWithAnim();
    }

    /**
     * 隐藏加载对话框
     */
    private void hideProgressWithAnim() {
        AnimationSet animation = getDismissAnim(this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        progressBar.startAnimation(animation);
    }

    /**
     * 获取消失的动画
     */
    private AnimationSet getDismissAnim(Context context) {
        AnimationSet dismiss = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        dismiss.addAnimation(alpha);
        return dismiss;
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
