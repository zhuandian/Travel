package com.zhuandian.travel.business.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.TravelGuideEntity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TravelGuideDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.iv_img)
    ImageView ivImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel_guide_detail;
    }

    @Override
    protected void setUpView() {
        TravelGuideEntity travelGuideEntity = (TravelGuideEntity) getIntent().getSerializableExtra("entity");
        Glide.with(this).load(travelGuideEntity.getImgUrl()).into(ivImg);
        tvName.setText(travelGuideEntity.getTitle());
        tvContent.setText(travelGuideEntity.getContent());
        tvType.setText(String.format("%s . %s", travelGuideEntity.getViewsTheme(), travelGuideEntity.getViewsLocal()));
        tvLevel.setText(String.format("时间等级%s  费用等级%s", travelGuideEntity.getTimeLevel() + 1, travelGuideEntity.getMoneyLevel() + 1));
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
