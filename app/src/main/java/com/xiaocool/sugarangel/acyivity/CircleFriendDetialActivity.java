package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.FriendListBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.DateUtils;
import com.xiaocool.sugarangel.utils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleFriendDetialActivity extends BaseActivity {
    @BindView(R.id.circle_detical_av)
    ImageView circleDeticalAv;
    @BindView(R.id.circle_detical_name)
    TextView circleDeticalName;
    @BindView(R.id.circle_detical_idnum)
    TextView circleDeticalIdnum;
    @BindView(R.id.circle_detical_age)
    TextView circleDeticalAge;
    @BindView(R.id.circle_detical_disease)
    TextView circleDeticalDisease;
    @BindView(R.id.circle_detical_treatment)
    TextView circleDeticalTreatment;
    @BindView(R.id.circle_detical_note)
    TextView circleDeticalNote;
    @BindView(R.id.circle_detical_message)
    Button circleDeticalMessage;
    private Context context;
    FriendListBean friendListBean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_circle_friend_detial;
    }

    @Override
    public void initView() {
        context = this;

        initdata();
    }

    private void initdata() {
        friendListBean = (FriendListBean) getIntent().getSerializableExtra("friend");
        setTitleBar(friendListBean.getName(),true);
        String time = ((DateUtils.dateToUnixTimestamp() / 1000 - Integer.parseInt(friendListBean.getTime()))) / 86400 + "";
        int age = (Integer.parseInt(DateUtils.getNowTime("yyyyMMdd")) - Integer.parseInt(DateUtils.getStrTime(friendListBean.getBirthday(), "yyyyMMdd")));
        GlideUtils.loadRectangImageView(context, NetConstant.SHOW_IMAGE + friendListBean.getPhoto(), circleDeticalAv);
        circleDeticalAge.setText("年龄：" + age / 10000 + "岁");
        circleDeticalTreatment.setText("治疗天数：" + time + "天");
        circleDeticalDisease.setText("病种：" + "服务器暂无此字段");
        circleDeticalIdnum.setText("ID号：" + friendListBean.getId());
        circleDeticalName.setText(friendListBean.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.circle_detical_note, R.id.circle_detical_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_detical_note:
                Intent intent = new Intent(context,FriendNoteActivity.class);
                intent.putExtra("id",friendListBean.getId());
                startActivity(intent);
                break;
            case R.id.circle_detical_message:
                break;
        }
    }
}
