package com.xiaocool.sugarangel.bean;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class PhoneFriendInfo implements Comparable<PhoneFriendInfo>{
    String name;
    String phone;
    String pinyin;
    public PhoneFriendInfo(){

    }
    public PhoneFriendInfo(String name,String phone, String pinyin) {
        this.phone = phone;
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(@NonNull PhoneFriendInfo another) {
        char[] chars = getPinyin().toCharArray();
        char[] anotherChars = another.getPinyin().toCharArray();

        int length = chars.length > anotherChars.length ? anotherChars.length:chars.length;

        for(int i = 0; i < length; i ++){
            if(chars[i] < anotherChars[i]){
                return -1;
            }else if(chars[i] > anotherChars[i]){
                return 1;
            }
        }
        return 0;
    }
}
