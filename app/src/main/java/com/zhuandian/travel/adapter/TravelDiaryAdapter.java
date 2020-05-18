package com.zhuandian.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.travel.BuildConfig;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.activity.TravelDiaryDetailActivity;
import com.zhuandian.travel.entity.TravelDiaryEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * date：2020/05/04
 */
public class TravelDiaryAdapter extends BaseAdapter<TravelDiaryEntity, BaseViewHolder> {
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

    public TravelDiaryAdapter(List<TravelDiaryEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, TravelDiaryEntity travelDiaryEntity, int position) {
        ButterKnife.bind(this,myViewHolder.itemView);

        tvName.setText(travelDiaryEntity.getTitle());
        tvContent.setText(travelDiaryEntity.getContent());
        ImageView[] imgs = new ImageView[]{ivPriviewImg1,ivPriviewImg2,ivPriviewImg3};

        tvTime.setText(travelDiaryEntity.getCreatedAt());
        ivPriviewImg1.setVisibility(View.GONE);
        ivPriviewImg2.setVisibility(View.GONE);
        ivPriviewImg3.setVisibility(View.GONE);
        tvTime.setText(travelDiaryEntity.getCreatedAt());
        for (int i = 0; i < travelDiaryEntity.getImgUrls().size(); i++) {
            imgs[i].setVisibility(View.VISIBLE);
            Glide.with(mContext).load(travelDiaryEntity.getImgUrls().get(i)).into(imgs[i]);
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TravelDiaryDetailActivity.class);
                intent.putExtra("entity",travelDiaryEntity);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_travel_diary;
    }
}
