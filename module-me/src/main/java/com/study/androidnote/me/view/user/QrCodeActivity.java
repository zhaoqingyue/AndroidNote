package com.study.androidnote.me.view.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 二维码
 */
public class QrCodeActivity extends BaseTopBarActivity {

    @BindView(R2.id.iv_qrcode)
    ImageView qrcode;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_qr_code;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String content = "I LOVE BYD.";
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(this, 150));
        if (bitmap != null) {
            qrcode.setImageBitmap(bitmap);
        } else {
            ToastUtils.showShortToast("生成二维码失败");
        }
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
