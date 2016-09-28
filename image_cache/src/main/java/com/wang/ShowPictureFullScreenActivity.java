package com.wang;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wang.java_util.TextUtil;
import com.wang.sliding_menu.R;

/**
 * 1.从Url获取图片，需要参数：imageUrl & cacheDir
 * <p/>
 * 2.从手机存储获取图片，需要参数：imagePath
 * <p/>
 * 3.从上一个Activity获取bitmap对象，需要参数：bitmap
 * <p/>
 * 从intent中传递
 */
public class ShowPictureFullScreenActivity extends Activity implements View.OnClickListener {

    private ImageView ivShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_picture_ful_screen);
        ivShow = (ImageView) findViewById(R.id.iv_show);
        ivShow.setOnClickListener(this);

        try {
            String imageUrl = getIntent().getStringExtra("imageUrl");
            String cacheDir = getIntent().getStringExtra("cacheDir");
            String imagePath = getIntent().getStringExtra("imagePath");
            Bitmap bitmap = getIntent().getParcelableExtra("bitmap");

            if (!TextUtil.isEmpty(imageUrl, cacheDir)) {
                showImageFromUrl(imageUrl, cacheDir);
            } else if (!TextUtil.isEmpty(imagePath)) {
                ivShow.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            } else if (bitmap != null) {
                ivShow.setImageBitmap(bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void showImageFromUrl(String imageUrl, String cacheDir) {
        ImageCacheUtil.startImageSDCardCache(ivShow, imageUrl, cacheDir,
                new ImageCacheUtil.OnGetImageFinishListener() {
                    @Override
                    public void onGetImageFinish(int state, String msg) {
                        if (state == ImageCacheUtil.STATE_FAILED) {
                            ivShow.setImageResource(R.mipmap.ic_load_failed);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
