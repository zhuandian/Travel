package com.zhuandian.travel.business.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.TravelDiaryEntity;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TravelDiaryDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_priview_img_1)
    ImageView ivPriviewImg1;
    @BindView(R.id.iv_priview_img_2)
    ImageView ivPriviewImg2;
    @BindView(R.id.iv_priview_img_3)
    ImageView ivPriviewImg3;
    @BindView(R.id.ll_photo_container)
    LinearLayout llPhotoContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel_diary_detail;
    }

    @Override
    protected void setUpView() {
        TravelDiaryEntity travelDiaryEntity = (TravelDiaryEntity) getIntent().getSerializableExtra("entity");
        tvName.setText(travelDiaryEntity.getTitle());
        tvContent.setText(travelDiaryEntity.getContent());
        ImageView[] imgs = new ImageView[]{ivPriviewImg1, ivPriviewImg2, ivPriviewImg3};

        tvTime.setText(travelDiaryEntity.getCreatedAt());
        for (int i = 0; i < travelDiaryEntity.getImgUrls().size(); i++) {
            Glide.with(this).load(travelDiaryEntity.getImgUrls().get(i)).into(imgs[i]);
        }

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
