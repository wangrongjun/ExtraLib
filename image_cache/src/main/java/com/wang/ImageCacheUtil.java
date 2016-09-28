package com.wang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.wang.android_lib.util.AnimationUtil;
import com.wang.java_util.DebugUtil;

import java.io.File;

import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageSDCardCache;
import cn.trinea.android.common.util.CacheManager;

/**
 *  by 王荣俊 on 2016/9/17.
 */
public class ImageCacheUtil {

    public static final int STATE_FAILED = -1;
    public static final int STATE_SUCCESS = 0;

    public interface OnGetImageFinishListener {
        void onGetImageFinish(int state, String msg);
    }

    private static ImageSDCardCache imageSDCardCache;

    /**
     * 使用ImageSDCardCache来下载缓存多个图片
     */
    public static void startImageSDCardCache(ImageView imageView, String imageUrl, String cacheDir,
                                             final OnGetImageFinishListener listener) {

        if (imageSDCardCache == null) {
            imageSDCardCache = CacheManager.getImageSDCardCache();
        }

        //        设置有效时间为60分钟
        imageSDCardCache.setValidTime(3600 * 1000);
        imageSDCardCache.setCacheFolder(cacheDir);
        imageSDCardCache.setOpenWaitingQueue(true);
        imageSDCardCache.setOnImageSDCallbackListener(new ImageSDCardCache.OnImageSDCallbackListener() {
            @Override
            public void onPreGet(String imageUrl, View view) {
                DebugUtil.println("开始加载：" + imageUrl);
                ((ImageView) view).setImageResource(com.wang.android_lib.R.mipmap.ic_loading_gray);
                view.startAnimation(AnimationUtil.getConstantSpeedRotateAnim(1000));
            }

            @Override
            public void onGetNotInCache(String imageUrl, View view) {
                DebugUtil.println("onGetNotInCache " + imageUrl);
            }

            @Override
            public void onGetSuccess(String imageUrl, String imagePath, View view, boolean isInCache) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                ((ImageView) view).setImageBitmap(bitmap);
                view.clearAnimation();
                if (listener != null) {
                    listener.onGetImageFinish(STATE_SUCCESS, null);
                }
            }

            @Override
            public void onGetFailed(String imageUrl, String imagePath, View view, FailedReason failedReason) {
                DebugUtil.println("onGetFailed " + imageUrl + "  " + failedReason.toString());
                view.clearAnimation();
                ((ImageView) view).setImageResource(com.wang.android_lib.R.mipmap.ic_load_failed);
                if (listener != null) {
                    listener.onGetImageFinish(STATE_FAILED, imageUrl + ": " + failedReason.toString());
                }
            }
        });

        imageSDCardCache.get(imageUrl, imageView);

    }

    public static void startImageSDCardCache(ImageView imageView, String imageUrl, String cacheDir) {
        startImageSDCardCache(imageView, imageUrl, cacheDir, null);
    }

    /**
     * 在本地缓存与服务器的图片不同步时使用。如用户上传头像后的更新。
     * 过程：先删除旧的本地缓存图片，再联网下载最新的图片。
     */
    public static void reloadUpdateImage(ImageView imageView, String imageUrl, String cacheDir,
                                         final OnGetImageFinishListener listener) {
        String imagePath = cacheDir + getImageCacheFileName(imageUrl);
        new File(imagePath).delete();
        startImageSDCardCache(imageView, imageUrl, cacheDir, listener);
    }

    public static String getImageCacheFileName(String imageUrl) {
        return CacheManager.getImageSDCardCache().getFileNameRule().getFileName(imageUrl);
    }
}
