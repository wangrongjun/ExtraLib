package com.wang;

import android.app.Activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wang.android_lib.util.ScreenUtil;

/**
 * https://github.com/jfeinstein10/SlidingMenu
 * http://blog.csdn.net/lmj623565791/article/details/36677279  Android SlidingMenu 使用详解
 * http://blog.csdn.net/lizzy115/article/details/38730143  SlidingMenu属性详解
 */
public class SlidingMenuUtil {

    /**
     * 使用：在Activity中创建后，设置背景，并调用toggle显示。
     */
    public static SlidingMenu buildDefault(Activity activity, int layoutRes) {
        SlidingMenu menu = new SlidingMenu(activity);
//        设置侧滑菜单的位置，可选值LEFT , RIGHT , LEFT_RIGHT （两边都有菜单时设置）
        menu.setMode(SlidingMenu.LEFT);
//         设置触摸屏幕的模式，可选只MARGIN , FULLSCREEN，NONE 
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        设置阴影的宽度
//        menu.setShadowWidth(10);
//        设置滑动菜单的阴影效果图
//        menu.setShadowDrawable(R.drawable.ic_loading);
//        设置菜单的宽度或离屏幕的偏移量，这两个都是设置滑动菜单视图的宽度，二选一
        menu.setBehindWidth((int) (ScreenUtil.getScreenWidth(activity) * 0.7));
//        menu.setBehindOffset(200);
//        设置是否渐入渐出效果以及其值
        menu.setFadeEnabled(true);
        menu.setFadeDegree(0.35f);
//        设置SlidingMenu与下方视图的移动的速度比，当为1时同时移动，取值0-1
        menu.setBehindScrollScale(0.2f);
//        设置二级菜单的阴影效果
//        menu.setSecondaryShadowDrawable(R.color.red);
//        为侧滑菜单设置布局
        menu.setMenu(layoutRes);
//        设置右边（二级）侧滑菜单
//        menu.setSecondaryMenu(R.layout.right_menu_frame);

//        把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
        menu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);

//        切换，关闭，打开
//        menu.toggle(true);
//        menu.showContent(true);
//        menu.showMenu(true);

//        设置监听
//        menu.setOnOpenListener(null);
//        menu.setOnOpenedListener(null);
//        menu.setOnCloseListener(null);
//        menu.setOnClosedListener(null);

        return menu;
    }
}
