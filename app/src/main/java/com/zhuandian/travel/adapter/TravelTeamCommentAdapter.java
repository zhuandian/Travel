package com.zhuandian.travel.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.CommentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 * date：2020/05/30
 */
public class TravelTeamCommentAdapter extends BaseAdapter<CommentEntity, BaseViewHolder> {

    @BindView(R.id.name)
    TextView commenterName;
    @BindView(R.id.time)
    TextView tvCommentTime;
    @BindView(R.id.content)
    TextView tvContent;

    public TravelTeamCommentAdapter(List<CommentEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder holder, CommentEntity commentEntity, int position) {
        ButterKnife.bind(this, holder.itemView);
        tvCommentTime.setText(commentEntity.getCreatedAt());
        tvContent.setText(commentEntity.getContent());
        commenterName.setText(commentEntity.getUserEntity().getUsername());   //设置评论者信息
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_travel_team_comment;
    }
}
