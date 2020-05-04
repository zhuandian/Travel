package com.zhuandian.travel.business.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.adapter.TravelGuideAdapter;
import com.zhuandian.travel.business.utils.BaseRecyclerView;
import com.zhuandian.travel.entity.TravelGuideEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GuideSearchResultActivity extends BaseActivity {

    @BindView(R.id.brv_list)
    BaseRecyclerView brvGoodsList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private List<TravelGuideEntity> mDatas = new ArrayList<>();
    private TravelGuideAdapter travelGuideAdapter;
    private int currentCount = -10;
    private String viewsLocal;
    private String viewsTheme;
    private int timeLevel;
    private int moneyLevel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_search_result;
    }

    @Override
    protected void setUpView() {
        viewsLocal = getIntent().getStringExtra("viewsLocal");
        viewsTheme = getIntent().getStringExtra("viewsTheme");
        timeLevel = getIntent().getIntExtra("timeLevel", 1);
        moneyLevel = getIntent().getIntExtra("moneyLevel", 1);
        tvTitle.setText("搜索结果");
        travelGuideAdapter = new TravelGuideAdapter(mDatas, this);
        brvGoodsList.setRecyclerViewAdapter(travelGuideAdapter);
        loadDatas();
        initRefreshListener();
    }

    private void initRefreshListener() {
        brvGoodsList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                travelGuideAdapter.notifyDataSetChanged();
                loadDatas();

            }
        });
        brvGoodsList.setLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDatas();
            }
        });


    }


    private void loadDatas() {
        currentCount = currentCount + 10;
        BmobQuery<TravelGuideEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.addWhereEqualTo("viewsLocal",viewsLocal);
        query.addWhereEqualTo("viewsTheme",viewsTheme);
        query.addWhereEqualTo("timeLevel",timeLevel);
        query.addWhereEqualTo("moneyLevel",moneyLevel);
        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<TravelGuideEntity>() {
            @Override
            public void done(List<TravelGuideEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    travelGuideAdapter.notifyDataSetChanged();
                    brvGoodsList.setRefreshLayoutState(false);
                } else {
                    brvGoodsList.setRefreshLayoutState(false);
                }
            }
        });
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        finish();
    }
}
