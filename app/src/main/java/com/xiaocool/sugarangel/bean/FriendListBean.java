package com.xiaocool.sugarangel.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class FriendListBean implements Comparable<FriendListBean>, Serializable {

    /**
     * id : 1
     * name : 糖友1
     * photo : yyy1497487820172.jpg
     * birthday : 0
     * time : 1495421454
     * morning_glucose :
     */

    private String id;
    private String name;
    private String photo;
    private String birthday;
    private String time;
    private String morning_glucose;
    String pinyin;

    public FriendListBean(String id, String name, String photo, String birthday, String time, String morning_glucose, String pinyin) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.birthday = birthday;
        this.time = time;
        this.morning_glucose = morning_glucose;
        this.pinyin = pinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMorning_glucose() {
        return morning_glucose;
    }

    public void setMorning_glucose(String morning_glucose) {
        this.morning_glucose = morning_glucose;
    }
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }



    @Override
    public int compareTo(@NonNull FriendListBean o) {
        char[] chars = getPinyin().toCharArray();
        char[] anotherChars = o.getPinyin().toCharArray();

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

