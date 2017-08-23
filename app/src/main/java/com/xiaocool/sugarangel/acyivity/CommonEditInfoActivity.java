package com.xiaocool.sugarangel.acyivity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.helper.InputHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hzh on 16/12/21.
 * 通用编辑信息页面
 */

public class CommonEditInfoActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.save_btn)
    TextView saveBtn;

    private Intent intent;
    private String titleStr, editStr;
    private int textCount;//字数限制
    private int editType;//输入内容限制
    private int numType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL;

    @Override
    public int getContentViewId() {
        return R.layout.activity_common_edit_info;
    }

    @Override
    public void initView() {
        intent = getIntent();
        titleStr = intent.getStringExtra("title");
        editStr = intent.getStringExtra("text");
        textCount = intent.getIntExtra("count", 0);
        editType = intent.getIntExtra("type", 0);

        title.setText(titleStr);
        if (!"请填写".equals(editStr)) {
            editText.setText(editStr);
        }
        if (textCount > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textCount)}); //最大输入长度
        }

        if (editType == InputHelper.NUM) {
            editText.setInputType(numType);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().length() == 0) {
                    saveBtn.setTextColor(getResources().getColor(R.color.title_black));
                    saveBtn.setClickable(false);
                } else {
                    saveBtn.setTextColor(getResources().getColor(R.color.white));
                    saveBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick({R.id.back, R.id.edit_text, R.id.save_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save_btn:
                String result = editText.getText().toString();
                if (result.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }
}
