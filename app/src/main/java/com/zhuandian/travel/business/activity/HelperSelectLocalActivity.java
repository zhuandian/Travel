package com.zhuandian.travel.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.MainActivity;
import com.zhuandian.travel.R;
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
import cn.bmob.v3.listener.UpdateListener;

public class HelperSelectLocalActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.fl_local)
    RadioGroup flLocal;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private String viewsLocal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_helper_select_local;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("选择地点");
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
                        RadioButton radioButton = new RadioButton(HelperSelectLocalActivity.this);
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

    @OnClick({R.id.iv_back, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                if (TextUtils.isEmpty(viewsLocal)){
                    Toast.makeText(this, "请先选择景点", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
                userEntity.setViewsLocal(viewsLocal);
                userEntity.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            startActivity(new Intent(HelperSelectLocalActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
                break;
        }
    }
}
