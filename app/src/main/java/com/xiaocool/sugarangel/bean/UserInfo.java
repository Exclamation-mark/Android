package com.xiaocool.sugarangel.bean;

import android.content.Context;

import com.xiaocool.sugarangel.sp.UserSp;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class UserInfo implements Serializable {
    /**
     * id : 1
     * name : 休闲钓鱼1
     * sex : 0
     * level : 1
     * score : 50
     * birthday : 0000-00-00
     * realname :
     * address :
     * phone : 15684152807
     * city :
     * email :
     * qq :
     * weixin :
     * photo : defaultavatar.png
     * school :
     * major :
     * education :
     * time : 1495421454
     * all_information : 0
     * usertype : 0
     * money : 0
     * fanscount : 0
     * followcount : 0
     */

    private String userid;
    private String name;
    private String sex;
    private String realname;
    private String address;
    private String photo;
    private boolean isBind;
    private String sn;
    private String deviceBinded;
    private String password;
    private String area;

    private String level;
    private String score;
    private String birthday;
    private String phone;
    private String city;
    private String email;
    private String qq;
    private String weixin;
    private String school;
    private String major;
    private String education;
    private String time;
    private String all_information;
    private String usertype;
    private int money;
    private String fanscount;
    private String followcount;
    private String devicestate;
    private String event;


    public UserInfo() {

    }

    public UserInfo(Context context) {
        readData(context);
    }

    public void readData(Context context) {
        UserSp sp = new UserSp(context);
        sp.read(this);

    }

    public void writeData(Context context) {
        UserSp sp = new UserSp(context);
        sp.write(this);
    }

    public void clearData(Context context) {
        UserSp sp = new UserSp(context);
        sp.clear();
    }

    public void clearDataExceptPhone(Context context) {
        UserSp sp = new UserSp(context);
        this.setPhone(sp.read().getPhone());
        sp.clear();
        sp.write(this);
    }


    public boolean isLogined() {
        return !this.getId().equals("");
    }

    public String getId() {
        if (userid == null||userid.equals("null")){
            return "";
        }
        return userid;
    }

    public void setId(String id) {
        this.userid = id;
    }

    public String getName() {
        if (name == null||name.equals("null")){
            return "";
        }
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        if (sex == null||sex.equals("null")){
            return "";
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevel() {
        if (level == null||level.equals("null")){
            return "";
        }
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        if (score == null||score.equals("null")){
            return "";
        }
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBirthday() {
        if (birthday == null||birthday.equals("null")){
            return "";
        }
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRealname() {
        if (realname == null||realname.equals("null")){
            return "";
        }
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        if (address == null||address.equals("null")){
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        if (phone == null||phone.equals("null")){
            return "";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        if (city == null||city.equals("null")){
            return "";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        if (email == null||email.equals("null")){
            return "";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        if (qq == null||qq.equals("null")){
            return "";
        }
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        if (weixin == null||weixin.equals("null")){
            return "";
        }
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getPhoto() {
        if (photo == null||photo.equals("null")){
            return "";
        }
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSchool() {
        if (school == null||school.equals("null")){
            return "";
        }
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        if (major == null||major.equals("null")){
            return "";
        }
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        if (education == null||education.equals("null")){
            return "";
        }
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTime() {
        if (time == null||time.equals("null")){
            return "";
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAll_information() {
        if (all_information == null||all_information.equals("null")){
            return "";
        }
        return all_information;
    }

    public void setAll_information(String all_information) {
        this.all_information = all_information;
    }

    public String getUsertype() {
        if (usertype == null||usertype.equals("null")){
            return "";
        }
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public int getMoney() {

        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getFanscount() {
        if (fanscount == null||fanscount.equals("null")){
            return "";
        }
        return fanscount;
    }

    public void setFanscount(String fanscount) {
        this.fanscount = fanscount;
    }

    public String getFollowcount() {
        if (followcount == null||followcount.equals("null")){
            return "";
        }
        return followcount;
    }

    public void setFollowcount(String followcount) {
        this.followcount = followcount;
    }

    public String getPassword() {
        if (password == null||password.equals("null")){
            return "";
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevicestate() {
        if (devicestate == null||devicestate.equals("null")){
            return "";
        }
        return devicestate;
    }

    public void setDevicestate(String devicestate) {
        this.devicestate = devicestate;
    }

    public String getEvent() {
        if (event == null||event.equals("null")){
            return "";
        }
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }

    public String getSn() {
        if (sn == null||sn.equals("null")){
            return "";
        }
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDeviceBinded() {
        if (deviceBinded == null||deviceBinded.equals("null")){
            return "";
        }
        return deviceBinded;
    }

    public void setDeviceBinded(String deviceBinded) {
        this.deviceBinded = deviceBinded;
    }

    public String getArea() {
        if (area == null||area.equals("null")){
            return "";
        }
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


}

