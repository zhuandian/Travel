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
import com.zhuandian.travel.business.activity.FoodsAndViewsDetailActivity;
import com.zhuandian.travel.entity.FoodsAndViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-29.
 */
public class FoodsAndViewsAdapter extends BaseAdapter<FoodsAndViewsEntity, BaseViewHolder> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_type)
    TextView tvType;

    public FoodsAndViewsAdapter(List<FoodsAndViewsEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, FoodsAndViewsEntity foodsAndViewsEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        tvTitle.setText(foodsAndViewsEntity.getTitle());
        tvContent.setText(foodsAndViewsEntity.getContent());
        tvType.setText(foodsAndViewsEntity.getType() == 1 ? "美食" : "美景");
        Glide.with(mContext).load(foodsAndViewsEntity.getUrl()).into(ivImg);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FoodsAndViewsDetailActivity.class);
                intent.putExtra("entity", foodsAndViewsEntity);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_foods_and_views;
    }
}
