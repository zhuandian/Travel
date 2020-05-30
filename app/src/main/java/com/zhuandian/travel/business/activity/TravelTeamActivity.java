package com.zhuandian.travel.business.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.adapter.TravelTeamAdapter;
import com.zhuandian.travel.business.utils.BaseRecyclerView;
import com.zhuandian.travel.entity.TravelTeamEntity;
import com.zhuandian.travel.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TravelTeamActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.brv_list)
    BaseRecyclerView brvList;
    @BindView(R.id.tv_new_travel_team)
    TextView tvNewTravelTeam;
    private List<TravelTeamEntity> mDatas = new ArrayList<>();
    private TravelTeamAdapter travelTeamAdapter;
    private int currentCount = -10;
    private String local;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel_team;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("旅游团列表");
        local = getIntent().getStringExtra("local");
        travelTeamAdapter = new TravelTeamAdapter(mDatas, this);
        brvList.setRecyclerViewAdapter(travelTeamAdapter);
        loadDatas();
        initRefreshListener();
        tvNewTravelTeam.setVisibility(BmobUser.getCurrentUser(UserEntity.class).getType() == 1 ? View.VISIBLE : View.GONE);
    }


    private void initRefreshListener() {
        brvList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                travelTeamAdapter.notifyDataSetChanged();
                loadDatas();

            }
        });
        brvList.setLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDatas();
            }
        });


    }


    private void loadDatas() {
        currentCount = currentCount + 10;
        BmobQuery<TravelTeamEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.include("userEntity");
        query.addWhereEqualTo("local",local);
        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<TravelTeamEntity>() {
            @Override
            public void done(List<TravelTeamEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    travelTeamAdapter.notifyDataSetChanged();
                    brvList.setRefreshLayoutState(false);
                } else {
                    brvList.setRefreshLayoutState(false);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_new_travel_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_new_travel_team:
                startActivity(new Intent(this, NewTravelTeamActivity.class));
                break;
        }
    }
}
