package com.xiaocool.sugarangel.acyivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.UserInfo;
import com.xiaocool.sugarangel.helper.PageIntentHelper;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.GlideUtils;
import com.xiaocool.sugarangel.utils.ImagePickerUtils;
import com.xiaocool.sugarangel.utils.L;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.xiaocool.sugarangel.utils.UploadImageUtils;
import com.xiaocool.sugarangel.view.CircleImageView;
import com.xiaocool.sugarangel.view.MyNavigationView;
import com.xiaocool.sugarangel.view.PicturePickerDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.Call;
import rx.functions.Action1;

public class MyInfoActivity extends BaseActivity {
    private final int NAME_CODE = 111;
    private final int SEX_CODE = 112;
    private final int ADDRESS_CODE = 113;
    private final int AREA_CODE = 114;
    @BindView(R.id.avatar_rl)
    RelativeLayout avatarRl;
    @BindView(R.id.avatar_iv)
    CircleImageView avatarIv;
    @BindView(R.id.myinfo_name)
    MyNavigationView myinfoName;
    @BindView(R.id.myinfo_id)
    MyNavigationView myinfoId;
    @BindView(R.id.myinfo_address)
    MyNavigationView myinfoAddress;
    @BindView(R.id.myinfo_gender)
    MyNavigationView myinfoGender;
    @BindView(R.id.myinfo_area)
    MyNavigationView myinfoArea;
    private PicturePickerDialog picturePickerDialog;//选择相机 相册dialog
    private ImagePickerUtils imagePickerUtils;//图片选择器
    private RxPermissions rxPermissions;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback;//图片选择器 监听器
    private Context mContext;
    private PhotoInfo avatarPhoto;
    UserInfo userInfo;

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initView() {
        setTitleBar("我的信息", true);
        mContext = this;
        userInfo = new UserInfo(mContext);
        picturePickerDialog = new PicturePickerDialog(mContext);
        imagePickerUtils = new ImagePickerUtils(1);
        rxPermissions = new RxPermissions(MyInfoActivity.this);
        mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                avatarPhoto = resultList.get(0);
                Log.d("avatarPhoto", avatarPhoto.getPhotoPath());
                UploadImageUtils.uploadImg(avatarPhoto.getPhotoPath(), new UploadImageUtils.OnUploadListener() {
                    @Override
                    public void onSuccess(String imgName) {
                        upDateUserAvatar(imgName);
                    }

                    @Override
                    public void onError(Exception e) {
                        ToastUtils.showShort(mContext, "上传头像失败");
                    }
                });
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ToastUtils.showShort(mContext, "图片选择失败");
            }
        };
        setData();
    }

    private void setData() {
        GlideUtils.showAvatar(mContext, NetConstant.SHOW_IMAGE + userInfo.getPhoto(), avatarIv);
        myinfoName.setHideStr(userInfo.getName());
        if (userInfo.getSex().equals("0")) {
            myinfoGender.setHideStr("女");
        } else if (userInfo.getSex().equals("1")) {
            myinfoGender.setHideStr("男");
        }
        myinfoAddress.setHideStr(userInfo.getAddress());
        myinfoArea.setHideStr(userInfo.getArea());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.avatar_rl, R.id.myinfo_name, R.id.myinfo_address, R.id.myinfo_gender, R.id.myinfo_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.avatar_rl:
                showPicturePickDialog();
                break;
            case R.id.myinfo_name:
                PageIntentHelper.goCommonEditAty(MyInfoActivity.this, myinfoName, NAME_CODE, 6);
                break;
            case R.id.myinfo_address:
                PageIntentHelper.goCommonEditAty(MyInfoActivity.this, myinfoAddress, ADDRESS_CODE, 50);
                break;
            case R.id.myinfo_gender:
                startActivityForResult(new Intent(mContext, GenderSelectActivity.class), SEX_CODE);
                break;
            case R.id.myinfo_area:
                PageIntentHelper.goCommonEditAty(MyInfoActivity.this, myinfoArea, AREA_CODE, 50);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case NAME_CODE:
                    String name = data.getStringExtra("result");
                    upDateName(name);
                    break;
                case ADDRESS_CODE:
                    String address = data.getStringExtra("result");
                    upDateAddress(address);
                    break;
                case AREA_CODE:
                    ToastUtils.showShort(mContext, "地区修改成功");
                    String area = data.getStringExtra("result");
                    upDateArea(area);
                    break;
                case SEX_CODE:
                    String sex = data.getStringExtra("sex");
                    upDateSex(sex);
                    break;
            }
        }
    }

    /*
    * 修改性别
    * */
    private void upDateSex(final String sex) {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.UPDATE_USER_SEX)
                .addParams("userid", userInfo.getId())
                .addParams("sex", sex)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        ToastUtils.showShort(mContext, "请求失败，请检查您的网络");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            L.d("username", response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
                                ToastUtils.showShort(mContext, "性别修改成功");
                                userInfo.setSex(sex);
                                if (sex.equals("0")) {
                                    myinfoGender.setHideStr("女");
                                } else if (sex.equals("1")) {
                                    myinfoGender.setHideStr("男");
                                }
                                userInfo.writeData(mContext);

                            } else {
                                ToastUtils.showShort(mContext, "性别修改失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();

                    }
                });
    }

    private void upDateArea(String area) {
        userInfo.setArea(area);
        userInfo.writeData(mContext);
        myinfoArea.setHideStr(userInfo.getArea());
    }

    /*
    * 修改地址
    * */
    private void upDateAddress(final String address) {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.UPDATE_USER_ADDRESS)
                .addParams("userid", userInfo.getId())
                .addParams("address", address)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        ToastUtils.showShort(mContext, "请求失败，请检查您的网络");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            L.d("username", response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
                                ToastUtils.showShort(mContext, "地址修改成功");
                                userInfo.setAddress(address);
                                userInfo.writeData(mContext);
                                myinfoAddress.setHideStr(userInfo.getAddress());
                            } else {
                                ToastUtils.showShort(mContext, "地址修改失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();

                    }
                });
    }

    /*
    * 修改个人资料-姓名
    * */
    private void upDateName(final String name) {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.UPDATE_USER_REALNAME)
                .addParams("userid", userInfo.getId())
                .addParams("realname", name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        ToastUtils.showShort(mContext, "请求失败，请检查您的网络");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            L.d("username", response);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
                                ToastUtils.showShort(mContext, "姓名修改成功");
                                userInfo.setName(name);
                                userInfo.writeData(mContext);
                                myinfoName.setHideStr(userInfo.getName());
                            } else {
                                ToastUtils.showShort(mContext, "姓名修改失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();

                    }
                });
    }

    /*
    * 修改个人资料-头像
    * */
    private void upDateUserAvatar(final String avatar) {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.UPDATE_USER_AVATAR)
                .addParams("userid", userInfo.getId())
                .addParams("avatar", avatar)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        ToastUtils.showShort(mContext, "请求失败，请检查您的网络");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
                                ToastUtils.showShort(mContext, "头像修改成功");
                                GlideUtils.loadImageView(mContext, avatarPhoto.getPhotoPath(), avatarIv);
                                userInfo.setPhoto(avatar);
                                userInfo.writeData(mContext);
                            } else {
                                ToastUtils.showShort(mContext, "头像修改失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();

                    }
                });
    }

    //选择照片弹框
    private void showPicturePickDialog() {

        picturePickerDialog.show(new PicturePickerDialog.PicturePickerCallBack() {
            @Override
            public void onPhotoClick() {
                imagePickerUtils.openSingleCamera(mContext, mOnHanlderResultCallback);
            }

            @Override
            public void onAlbumClick() {
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    imagePickerUtils.openSingleAblum(mContext, mOnHanlderResultCallback);
                                }
                            }
                        });
            }
        });
    }


}
