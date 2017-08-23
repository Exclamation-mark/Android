package com.xiaocool.sugarangel.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xiaocool.sugarangel.acyivity.CommonEditInfoActivity;
import com.xiaocool.sugarangel.view.MyNavigationView;

/**
 * Created by hzh on 16/12/12.
 * 页面跳转帮助类
 */

public class PageIntentHelper {



    /**
     * @param context
     * @param nv
     * @param requestCode 跳转到编辑页面
     */
    public static void goCommonEditAty(Activity context, MyNavigationView nv, int requestCode) {
        Intent intent = new Intent(context, CommonEditInfoActivity.class);
        intent.putExtra("title", nv.getTitleStr());
        intent.putExtra("text", nv.getHideStr());
        context.startActivityForResult(intent, requestCode);
    }

    public static void goCommonEditAty(Activity context, MyNavigationView nv, int requestCode,
                                       int textCount) {
        Intent intent = new Intent(context, CommonEditInfoActivity.class);
        intent.putExtra("title", nv.getTitleStr());
        intent.putExtra("text", nv.getHideStr());
        intent.putExtra("count", textCount);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * @param context
     * @param nv
     * @param requestCode
     * @param textCount   字数限制
     * @param editType    输入类型 数字或字母 使用InputHelper
     */
    public static void goCommonEditAty(Activity context, MyNavigationView nv, int requestCode,
                                       int textCount, int editType) {
        Intent intent = new Intent(context, CommonEditInfoActivity.class);
        intent.putExtra("title", nv.getTitleStr());
        intent.putExtra("text", nv.getHideStr());
        intent.putExtra("count", textCount);
        intent.putExtra("type", editType);
        context.startActivityForResult(intent, requestCode);
    }


}
