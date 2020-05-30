package com.zhuandian.travel.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.adapter.TravelTeamAdapter;
import com.zhuandian.travel.entity.ViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChoseLocalActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.fl_local)
    RadioGroup flLocal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chose_local;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("选择旅游景点");
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
                        RadioButton radioButton = new RadioButton(ChoseLocalActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String viewsLocal=list.get(finalI).getName();
                                Intent intent = new Intent(ChoseLocalActivity.this, TravelTeamActivity.class);
                                intent.putExtra("local",viewsLocal);
                                startActivity(intent);
                                finish();
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
