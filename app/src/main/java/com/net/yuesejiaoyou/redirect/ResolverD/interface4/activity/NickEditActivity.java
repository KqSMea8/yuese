package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class NickEditActivity extends BaseActivity {
    @BindView(R.id.edit_nickname)
    EditText edit_nickname;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent1 = getIntent();
        String old_name = intent1.getStringExtra("old_name");
        edit_nickname.setText(old_name);
        edit_nickname.setSelection(old_name.length());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_nickedit;
    }

    @OnClick(R.id.back1)
    public void backClick(){
        finish();
    }

    @OnClick(R.id.besure)
    public void comfirmClick(){
        String str = edit_nickname.getText().toString();
        if(TextUtils.isEmpty(str)){
            showToast("请输入昵称");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("new_name",str);
        setResult(RESULT_OK, intent);
        finish();
    }
}
