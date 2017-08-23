package com.xiaocool.sugarangel.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hzh on 16/12/28.
 */

public class ToastUtils {

    public static void showShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
