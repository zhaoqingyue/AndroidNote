package com.study.androidnote.me.view.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.manager.UserInfoManager;
import com.study.biz.manager.UserManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 二维码
 */
public class QrCodeActivity extends BaseTopBarActivity {

    @BindView( R2.id.iv_avatar)
    ImageView mAvatar;

    @BindView(R2.id.tv_nick)
    TextView mNickName;

    @BindView(R2.id.iv_sex)
    ImageView mSex;

    @BindView(R2.id.tv_area)
    TextView mArea;

    @BindView(R2.id.iv_qrcode)
    ImageView mQrCode;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_qr_code;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        updateUserInfo();
        makeQrCode();
    }

    private void updateUserInfo() {
        UserInfo userInfo = UserInfoManager.getInstance();
        if (userInfo == null)
            return;

        // 头像
        String avatar = userInfo.getAvatar();
        Glide.with(this)
                .load(avatar)
                .error(R.mipmap.biz_avatar)
                .placeholder(R.mipmap.biz_avatar)
                .into(mAvatar);

        // 昵称
        String nick = userInfo.getNickName();
        if (TextUtils.isEmpty(nick)) {
            nick = getString(R.string.me_user_hint_unset);
        }
        mNickName.setText(nick);

        // 性别
        String sex = userInfo.getSex();
        if (TextUtils.isEmpty(sex)) {
            sex = "--";
        }
        int sexIndex = UserManager.getInstance().getSexPosByName(sex);
        if (sexIndex == 0) {
            mSex.setImageResource(R.mipmap.me_sex_male);
        } else if (sexIndex == 1) {
            mSex.setImageResource(R.mipmap.me_sex_female);
        }

        // 地区
        String area = userInfo.getArea();
        if (TextUtils.isEmpty(area)) {
            area = "--";
        }
        mArea.setText(area);
    }

    private void makeQrCode() {
        String content = "I LOVE BYD.";
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(this, 150));
        if (bitmap != null) {
            mQrCode.setImageBitmap(bitmap);
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
