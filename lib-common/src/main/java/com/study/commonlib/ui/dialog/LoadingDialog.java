package com.study.commonlib.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;
import com.study.commonlib.R;

/**
 * Created by zhao.qingyue on 2019/8/23.
 * 加载弹框
 */
public class LoadingDialog extends Dialog {

    private static LoadingDialog mDialog = null;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 创建进度条
     */
    private static LoadingDialog createDialog(Context context, final String msg) {
        if (context != null) {
            final Activity act = (Activity) context;
            if (null != act) {
                act.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (mDialog == null) {
                            mDialog = new LoadingDialog(act, R.style.DialogStyle);
                            mDialog.setContentView(R.layout.dialog_loading);
                            mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
                            mDialog.setCanceledOnTouchOutside(false);

                            if (!TextUtils.isEmpty(msg)) {
                                TextView tv_msg = (TextView) mDialog.findViewById(R.id.progress_content);
                                if (null != tv_msg) {
                                    tv_msg.setText(msg);
                                }
                            }
                            mDialog.show();
                        }
                    }
                });
            }
        }
        return mDialog;
    }

    /**
     * 显示等待进度条弹出框
     */
    public static LoadingDialog showProgress(Context context) {
        createDialog(context, null);
        return mDialog;
    }

    /**
     * 显示等待进度条弹出框
     */
    public static LoadingDialog showProgress(Context context, String message) {
        createDialog(context, message);
        return mDialog;
    }

    /**
     * 等待进度条弹出框是否显示
     */
    public static boolean isShowProgress() {
        return (null != mDialog && mDialog.isShowing());
    }

    /**
     * 取消等待进度条弹出框
     */
    public static void cancelProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
            mDialog = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cancelProgress();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cancelProgress();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
