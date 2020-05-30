package com.zhuandian.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.activity.TravelTeamCommentActivity;
import com.zhuandian.travel.entity.TravelTeamEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.http.I;

/**
 * desc :
 * author：xiedong
 * date：2020/05/30
 */
public class TravelTeamAdapter extends BaseAdapter<TravelTeamEntity, BaseViewHolder> {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public TravelTeamAdapter(List<TravelTeamEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, TravelTeamEntity travelTeamEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        tvContent.setText(travelTeamEntity.getContent());
        tvName.setText(travelTeamEntity.getTitle());
        tvUser.setText("发布人：" + travelTeamEntity.getUserEntity().getUsername());
        tvTime.setText(travelTeamEntity.getCreatedAt());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TravelTeamCommentActivity.class);
                intent.putExtra("entity",travelTeamEntity);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_travel_team;
    }
}
