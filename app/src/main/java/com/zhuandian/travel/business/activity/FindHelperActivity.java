package com.zhuandian.travel.business.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.UserEntity;
import com.zhuandian.travel.entity.ViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindHelperActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.fl_local)
    RadioGroup flLocal;
    @BindView(R.id.ll_user_container)
    LinearLayout llUserContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_helper;
    }

    @Override
    protected void setUpView() {
        initLocal();


    }

    private void getHelper(String viewsLocal) {
        BmobQuery<UserEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("type", 1);
        query.addWhereEqualTo("viewsLocal",viewsLocal);
        query.findObjects(new FindListener<UserEntity>() {
            @Override
            public void done(List<UserEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        TextView textView = new TextView(FindHelperActivity.this);
                        textView.setText("       用户: " + list.get(i).getUsername());
                        textView.setGravity(Gravity.CENTER_VERTICAL);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));

                        int finalI = i;
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(FindHelperActivity.this)
                                        .setTitle("志愿者信息")
                                        .setMessage("志愿者名字："+list.get(finalI).getUsername()+"\n"+"电话号码：\n" + list.get(finalI).getMobilePhoneNumber())
                                        .show();
                            }
                        });
                        llUserContainer.addView(textView);
                    }
                }
            }
        });
    }

    private void initLocal() {
        BmobQuery<ViewsEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ViewsEntity>() {
            @Override
            public void done(List<ViewsEntity> list, BmobException e) {
                if (e == null) {
                    flLocal.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        RadioButton radioButton = new RadioButton(FindHelperActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String viewsLocal=list.get(finalI).getName();
                                getHelper(viewsLocal);
                            }
                        });
                        flLocal.addView(radioButton);
                    }

                }
            }
        });
    }



    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
