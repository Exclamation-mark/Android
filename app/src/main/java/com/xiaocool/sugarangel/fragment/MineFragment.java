package com.xiaocool.sugarangel.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.acyivity.DrugManagementActivity;
import com.xiaocool.sugarangel.acyivity.HelpActivity;
import com.xiaocool.sugarangel.acyivity.InviteActivity;
import com.xiaocool.sugarangel.acyivity.MyAccountActivity;
import com.xiaocool.sugarangel.acyivity.MyInfoActivity;
import com.xiaocool.sugarangel.acyivity.PatientManagementActivity;
import com.xiaocool.sugarangel.acyivity.SettingActivity;
import com.xiaocool.sugarangel.bean.UserInfo;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.GlideUtils;
import com.xiaocool.sugarangel.view.CircleImageView;
import com.xiaocool.sugarangel.view.MyNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.我的
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_use_day)
    TextView mineUseDay;
    @BindView(R.id.mine_account_nv)
    MyNavigationView mineAccountNv;
    @BindView(R.id.mine_patient_management)
    MyNavigationView minePatientManagement;
    @BindView(R.id.mine_drug_management)
    MyNavigationView mineDrugManagement;
    @BindView(R.id.mine_setting)
    MyNavigationView mineSetting;
    @BindView(R.id.mine_help)
    MyNavigationView mineHelp;
    @BindView(R.id.mine_invite)
    MyNavigationView mineInvite;
    Unbinder unbinder;
    Context mContext;
    @BindView(R.id.mine_avatar)
    CircleImageView mineAvatar;

    UserInfo userInfo;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        userInfo = new UserInfo(mContext);
        setData();
    }

    private void setData() {
        GlideUtils.showAvatar(mContext, NetConstant.SHOW_IMAGE + userInfo.getPhoto(), mineAvatar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mine_avatar, R.id.mine_account_nv, R.id.mine_patient_management, R.id.mine_drug_management, R.id.mine_setting, R.id.mine_help, R.id.mine_invite})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.mine_avatar:
                intent = new Intent(mContext, MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_account_nv:
                intent = new Intent(mContext, MyAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_patient_management:
                intent = new Intent(mContext, PatientManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_drug_management:
                intent = new Intent(mContext, DrugManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:
                intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_help:
                intent = new Intent(mContext, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_invite:
                intent = new Intent(mContext, InviteActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
