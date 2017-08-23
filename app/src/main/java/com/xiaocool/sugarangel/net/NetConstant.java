package com.xiaocool.sugarangel.net;

/**
 * Created by hzh on 17/1/9.
 */

public interface NetConstant extends BaseConstant {

    /**
     * 注册
     */
    String REGISTER = NET_HOST + "a=AppRegister";

    /**
     * 验证手机号是否注册
     */
    String PHONE_IS_REGISTER = NET_HOST + "a=checkphone";

    /**
     * 登录
     */
    String LOGIN = NET_HOST + "a=applogin";

    /**
     * 获取验证码
     */
    String GET_MOBILE_CODE = NET_HOST + "a=SendMobileCode";
    /**
     * 忘记密码
     */
    String FORGET_PW = NET_HOST + "a=forgetpwd";
    /**
     * 绑定设备
     */
    String BIND_DEVICE = NET_HOST + "a=BindDeviceByPhoneSN";
    /**
     * 上传头像
     */
    String UPLOAD_IMAGE = NET_HOST + "a=uploadavatar";
    /**
     * 修改头像
     */
    String UPDATE_USER_AVATAR = NET_HOST + " a=UpdateUserAvatar";
    /**
     * 修改姓名
     */
    String UPDATE_USER_REALNAME = NET_HOST + " a=UpdateUserRealName";
    /**
     * 修改地址
     */
    String UPDATE_USER_ADDRESS = NET_HOST + " a=UpdateUserAddress";
    /**
     * 修改性别
     */
    String UPDATE_USER_SEX = NET_HOST + " a=UpdateUserSex";

    /**
     * 上传数据
     */
    String ADD_DEVICE_DATA_BYUSERIDSN = NET_HOST + "a=AddDeviceDataByUserIDSN";
    /**
     * 获取设备数据
     */
    String GET_DEVICE_DATA = NET_HOST + "a=get_device_data_list_by_userid";
    /**
     * 获取设备数据
     */
    String CHECK_BIND_DEVICE = NET_HOST + "a=CheckBindDeviceByUserIDSN";

    /**
     * 获取亲属列表
     */
    String GET_USER_RELATION_SHIP = NET_HOST + "a=getUserRelationShip";
    /**
     * 获取积分
     */
    String GET_USER_SCORE = NET_HOST + "a=getUserScore";
    /**
     * 获取积分
     */
    String GetFriendList = NET_HOST + "a=GetFriendList";
    /**
     * 显示图片统一接口
     * http://yanglao.xiaocool.net/data/images/photo/文件名.jpg
     * http://yanglao.xiaocool.net/uploads/images/
     */
    String SHOW_IMAGE = "http://tang.xiaocool.net/uploads/microblog/";


}
