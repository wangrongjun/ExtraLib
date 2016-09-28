package com.wang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.wang.android_lib.util.IntentUtil;
import com.wang.android_lib.util.M;
import com.wang.qr_scan.R;

/**
 * by 王荣俊 on 2016/9/12.
 * <p/>
 * https://github.com/yipianfengye/android-zxingLibrary 二维码扫描框架
 * 需要添加依赖：compile 'cn.yipianfengye.android:zxing-library:1.9'
 * <p/>
 * 需要的权限：
 * <uses-permission android:name="android.permission.FLASHLIGHT" />
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-feature android:name="android.hardware.camera" />
 * <uses-feature android:name="android.hardware.autofocus" />
 * <p/>
 * 需要初始化：Application-onCreate或Activity-onCreate：
 * ZXingLibrary.initDisplayOpinion(this);
 * <p/>
 * 使用startActivityForResult进入此页面，扫描完成后返回intent键为"result"的字符串。
 * 若result==null，说明扫描失败。若result==""，说明扫描成功，但二维码本身没有结果。
 */
public class QrCaptureActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView ivBulb;
    private boolean isLight = false;

    public static final int FROM_CAMERA = 1;//由相机获得二维码信息
    public static final int FROM_IMAGE = 2;//由本地图片获得二维码信息

    public static int fromCode = FROM_CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_qr_capture);

        initView();

        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_scan, captureFragment).commit();
    }

    private void initView() {
        ivBulb = (ImageView) findViewById(R.id.iv_bulb);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_bar_code).setOnClickListener(this);
        findViewById(R.id.btn_local_image).setOnClickListener(this);
        findViewById(R.id.btn_flashlight).setOnClickListener(this);
    }

    private CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent data = new Intent();
            if (result == null) result = "";
            data.putExtra("result", result);
            setResult(fromCode, data);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent data = new Intent();
            setResult(fromCode, data);
            finish();
        }
    };

    private void changeFlashlightState() {
        if (isLight) {
            CodeUtils.isLightEnable(false);
            ivBulb.setImageResource(R.mipmap.ic_bulb_gray);
            isLight = false;
        } else {
            CodeUtils.isLightEnable(true);
            ivBulb.setImageResource(R.mipmap.ic_bulb_green);
            isLight = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                String path = IntentUtil.getRealPathFromURI(QrCaptureActivity.this, uri);
                fromCode = FROM_IMAGE;
                CodeUtils.analyzeBitmap(path, analyzeCallback);
                finish();
            }
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_back) {
            finish();

        } else if (v.getId() == R.id.btn_bar_code) {
            M.t(this, "暂不支持");

        } else if (v.getId() == R.id.btn_local_image) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 0);

        } else if (v.getId() == R.id.btn_flashlight) {
            changeFlashlightState();

        }

    }

}
