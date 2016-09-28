package com.wang.common_lib;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * by 王荣俊 on 2016/9/10.
 * http://www.cnblogs.com/huangjianboke/p/5447281.html 自定义 Material Design风格的提示框
 * <p/>需要添加依赖：
 * compile 'me.drakeet.materialdialog:library:1.3.1'
 * <p/>
 * https://github.com/drakeet/MaterialDialog
 */
public class MaterialDialogUtil {

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

    public interface OnInputFinishListener {
        void onInputFinish(String input);
    }

    public static void showComfirm(Context context, String title, String message,
                                   final OnConfirmClickListener listener) {

        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle(title).setMessage(message);
        dialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onConfirmClick();
                }
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showInput(Context context, String title, String text,
                                 final OnInputFinishListener listener) {

        final EditText editText = new EditText(context);
        editText.setText(text);
        editText.requestFocus();

        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle(title).setContentView(editText);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onInputFinish(editText.getText().toString());
                }
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
