package com.zhuandian.travel.business.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.UserEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SelectFriendActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_user_container)
    LinearLayout llUserContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_friend;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("匹配旅友");
        UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
        BmobQuery<UserEntity> query = new BmobQuery<>();
        query.addWhereNotEqualTo("type", 1);
        query.addWhereEqualTo("gender", currentUser.getGender());
        query.addWhereEqualTo("matchLocal", currentUser.getMatchLocal());
        query.findObjects(new FindListener<UserEntity>() {
            @Override
            public void done(List<UserEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        TextView textView = new TextView(SelectFriendActivity.this);
                        textView.setText("       用户: " + list.get(i).getUsername());
                        textView.setGravity(Gravity.CENTER_VERTICAL);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));

                        int finalI = i;
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(SelectFriendActivity.this)
                                        .setTitle("旅友信息")
                                        .setMessage("好友名字：" + list.get(finalI).getUsername() + "\n" + "电话号码：\n" + list.get(finalI).getMobilePhoneNumber())
                                        .show();
                            }
                        });
                        if (!list.get(i).getObjectId().equals(currentUser.getObjectId()))
                            llUserContainer.addView(textView);
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
