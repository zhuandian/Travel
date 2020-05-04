package com.zhuandian.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.activity.TravelGuideDetailActivity;
import com.zhuandian.travel.entity.TravelGuideEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * date：2020/05/04
 */
public class TravelGuideAdapter extends BaseAdapter<TravelGuideEntity, BaseViewHolder> {
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_level)
    TextView tvLevel;

    public TravelGuideAdapter(List<TravelGuideEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, TravelGuideEntity travelGuideEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        Glide.with(mContext).load(travelGuideEntity.getImgUrl()).into(ivImg);
        tvName.setText(travelGuideEntity.getTitle());
        tvContent.setText(travelGuideEntity.getContent());
        tvType.setText(String.format("%s . %s", travelGuideEntity.getViewsTheme(), travelGuideEntity.getViewsLocal()));
        tvLevel.setText(String.format("时间等级%s  费用等级%s", travelGuideEntity.getTimeLevel()+1, travelGuideEntity.getMoneyLevel()+1));

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TravelGuideDetailActivity.class);
                intent.putExtra("entity",travelGuideEntity);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_travel_guide

                ;
    }
}
