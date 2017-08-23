package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.UserInfo;
import com.xiaocool.sugarangel.helper.SPHelper;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.SPUtils;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {
    private static final int REGISTER_SUCCESS = 1010;
    @BindView(R.id.login_phone_edt)
    EditText loginPhoneEdt;
    @BindView(R.id.login_pw_edt)
    EditText loginPwEdt;
    @BindView(R.id.login_goregister_tv)
    TextView loginGoregisterTv;
    @BindView(R.id.login_forget_pw_tv)
    TextView loginForgetPwTv;
    @BindView(R.id.login_confirm_btn)
    Button loginConfirmBtn;
    Context mContext;
    UserInfo userInfo;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mContext = this;
        userInfo = new UserInfo(mContext);
        loginPhoneEdt.setText("15684160915");
        loginPwEdt.setText("123");
//        if (userInfo.isLogined()){
        String phone = userInfo.getPhone();
        String password = userInfo.getPassword();
//            login(phone, password);
//        }
    }

    @OnClick({R.id.login_goregister_tv, R.id.login_forget_pw_tv, R.id.login_confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_goregister_tv:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, REGISTER_SUCCESS);

                break;
            case R.id.login_forget_pw_tv:
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
            case R.id.login_confirm_btn:
                String phone = loginPhoneEdt.getText().toString();
                String password = loginPwEdt.getText().toString();

                if (phone.equals("") || password.equals("")) {
                    ToastUtils.showShort(mContext, "请输入账号密码");
                } else {
                    login(phone, password);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REGISTER_SUCCESS:
                    String phone = (String) SPUtils.get(mContext, SPHelper.USER_PHONE, "");
                    String password = (String) SPUtils.get(mContext, SPHelper.PASSWORD, "");

                    login(phone, password);
                    break;
            }
        }
    }

    private void login(final String phone, final String password) {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        if (TextUtils.isEmpty(password)) {
            return;
        }
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.LOGIN)
                .addParams("phone", phone)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
                                String s = jsonObject.getString("data");
                                Gson gson = new Gson();
                                userInfo = gson.fromJson(s, UserInfo.class);
                                userInfo.setPassword(password);
                                userInfo.writeData(mContext);
                                ToastUtils.showShort(mContext, "登录成功");
                                finish();
                                startActivity(new Intent(mContext, MainActivity.class));
                            } else {
                                ToastUtils.showShort(mContext, "登录失败，账号密码错误");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }
                });
    }

}
