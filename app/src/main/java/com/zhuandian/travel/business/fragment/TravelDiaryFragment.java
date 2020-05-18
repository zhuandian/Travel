package com.zhuandian.travel.business.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.R;
import com.zhuandian.travel.adapter.TravelDiaryAdapter;
import com.zhuandian.travel.business.activity.NewTravelDiaryActivity;
import com.zhuandian.travel.business.utils.BaseRecyclerView;
import com.zhuandian.travel.entity.TravelDiaryEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * desc :
 * author：xiedong
 */
public class TravelDiaryFragment extends BaseFragment {

    @BindView(R.id.brv_list)
    BaseRecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_new)
    TextView tvNew;
    private List<TravelDiaryEntity> mDatas = new ArrayList<>();
    private TravelDiaryAdapter travelDiaryAdapter;
    private int currentCount = -10;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_travel_diary;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("旅游日记");

        travelDiaryAdapter = new TravelDiaryAdapter(mDatas,actitity);
        recyclerView.setRecyclerViewAdapter(travelDiaryAdapter);
        loadDatas();
        initRefreshListener();
    }


    private void initRefreshListener() {
        recyclerView.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                travelDiaryAdapter.notifyDataSetChanged();
                loadDatas();

            }
        });
        recyclerView.setLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDatas();
            }
        });


    }


    private void loadDatas() {
        currentCount = currentCount + 10;
        BmobQuery<TravelDiaryEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<TravelDiaryEntity>() {
            @Override
            public void done(List<TravelDiaryEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    travelDiaryAdapter.notifyDataSetChanged();
                    recyclerView.setRefreshLayoutState(false);
                } else {
                    recyclerView.setRefreshLayoutState(false);
                }
            }
        });
    }

    @OnClick(R.id.tv_new)
    public void onViewClicked() {
        startActivity(new Intent(actitity, NewTravelDiaryActivity.class));
    }


}
