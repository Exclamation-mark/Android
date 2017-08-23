package com.xiaocool.sugarangel.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiaocool.sugarangel.bean.UserInfo;


/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class UserSp extends BaseSp<UserInfo> {
    public UserSp(Context context) {
        super(context, "user_sp");
    }

    @Override
    public UserInfo read() {
        UserInfo result = null;
        result = new UserInfo();
        read(result);
        return result;

    }

    @Override
    public void read(UserInfo user) {
// 安全检查
        if (user == null) {
            user = new UserInfo();
        }
        if (getSP().contains("userId")) {
            user.setId(getSP().getString("userId", ""));
        }
        if (getSP().contains("userPhone")) {
            user.setPhone(getSP().getString("userPhone", ""));
        }
        if (getSP().contains("userCode")) {
            user.setAddress(getSP().getString("userCode", ""));
        }
        if (getSP().contains("userName")) {
            user.setName(getSP().getString("userName", ""));
        }
        if (getSP().contains("userPassword")) {
            user.setPassword(getSP().getString("userPassword", ""));
        }
        if (getSP().contains("userGender")) {
            user.setSex(getSP().getString("userGender", ""));
        }

        if (getSP().contains("userImg")) {
            user.setPhoto(getSP().getString("userImg", ""));
        }
        if (getSP().contains("userBirthday")) {
            user.setBirthday(getSP().getString("userBirthday", ""));
        }
        if (getSP().contains("userIsBind")) {
            user.setBind(getSP().getBoolean("userIsBind",false));
        }
        if (getSP().contains("userSn")) {
            user.setSn(getSP().getString("userSn",""));
        }
        if (getSP().contains("userDeviceBinded")) {
            user.setDeviceBinded(getSP().getString("userDeviceBinded",""));
        }
    }

    @Override
    public void write(UserInfo user) {
        SharedPreferences.Editor editor = getSP().edit();
        if (!user.getId().equals("")) {
            editor.putString("userId", user.getId());
        }
        if (!user.getPhone().equals("")) {
            editor.putString("userPhone", user.getPhone());
        }
        if (!user.getAddress().equals("")) {
            editor.putString("userCode", user.getAddress());
        }
        if (!user.getName().equals("")) {
            editor.putString("userName", user.getName());
        }
        if (!user.getPassword().equals("")) {
            editor.putString("userPassword", user.getPassword());
        }
        if (!user.getSex().equals("")) {
            editor.putString("userGender", user.getSex());
        }
        if (!user.getPhoto().equals("")) {
            editor.putString("userImg", user.getPhoto());
        }

        if (!user.getBirthday().equals("")) {
            editor.putString("userBirthday", user.getBirthday());
        }
        if (user.isBind()) {
            editor.putBoolean("userIsBind", user.isBind());
        }
        if (!user.getSn().equals("")) {
            editor.putString("userSn", user.getSn());
        }
        if (!user.getDeviceBinded().equals("")) {
            editor.putString("userDeviceBinded", user.getDeviceBinded());
        }
        editor.commit();
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = getSP().edit();
        editor.clear();
        editor.commit();
    }
}
