package com.zhuandian.travel.business.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.FoodsAndViewsEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodsAndViewsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_item_title)
    TextView tvItemTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_foods_and_views_detail;
    }

    @Override
    protected void setUpView() {
        FoodsAndViewsEntity foodsAndViewsEntity = (FoodsAndViewsEntity) getIntent().getSerializableExtra("entity");
        tvTitle.setText(foodsAndViewsEntity.getTitle());
        tvItemTitle.setText(foodsAndViewsEntity.getTitle());
        tvContent.setText(foodsAndViewsEntity.getContent());
        tvType.setText(foodsAndViewsEntity.getType() == 1 ? "美食" : "美景");
        Glide.with(this).load(foodsAndViewsEntity.getUrl()).into(ivImg);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
