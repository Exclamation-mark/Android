package com.xiaocool.sugarangel.bean;

import android.support.annotation.NonNull;

/**
 * Created by TpOut on 2016/7/23.
 * Email: 416756910@qq.com
 */
public class InfoBean implements Comparable<InfoBean> {

    String name;
    String pinyin;

    public InfoBean(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
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
    public int compareTo(@NonNull InfoBean another) {
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
