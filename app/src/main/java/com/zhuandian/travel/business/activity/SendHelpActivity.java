package com.zhuandian.travel.business.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.SendHelpEntity;
import com.zhuandian.travel.entity.UserEntity;
import com.zhuandian.travel.entity.ViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SendHelpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rb_help_level_1)
    RadioButton rbHelpLevel1;
    @BindView(R.id.rb_help_level_2)
    RadioButton rbHelpLevel2;
    @BindView(R.id.rb_help_level_3)
    RadioButton rbHelpLevel3;
    @BindView(R.id.fl_local)
    RadioGroup flLocal;
    @BindView(R.id.tv_send_help)
    TextView tvSendHelp;
    private String viewsLocal;
    private int helpLevel=-1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_help;
    }

    @Override
    protected void setUpView() {
        initLocal();
    }


    private void initLocal() {
        BmobQuery<ViewsEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ViewsEntity>() {
            @Override
            public void done(List<ViewsEntity> list, BmobException e) {
                if (e == null) {
                    flLocal.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        RadioButton radioButton = new RadioButton(SendHelpActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewsLocal = list.get(finalI).getName();
                            }
                        });
                        flLocal.addView(radioButton);
                    }

                }
            }
        });
    }




    @OnClick({R.id.iv_back, R.id.rb_help_level_1, R.id.rb_help_level_2, R.id.rb_help_level_3, R.id.tv_send_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.rb_help_level_1:
                helpLevel = 1;
                break;
            case R.id.rb_help_level_2:
                helpLevel = 2;
                break;
            case R.id.rb_help_level_3:
                helpLevel = 3;
                break;
            case R.id.tv_send_help:
                sendHelp();
                break;
        }
    }

    private void sendHelp() {
        SendHelpEntity sendHelpEntity = new SendHelpEntity();
        sendHelpEntity.setHelperLevel(helpLevel);
        sendHelpEntity.setHelperLocal(viewsLocal);
        sendHelpEntity.setHelpState(0);
        sendHelpEntity.setHelperUser(BmobUser.getCurrentUser(UserEntity.class));
        sendHelpEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Toast.makeText(SendHelpActivity.this, "发送呼救成功...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
