package com.zhuandian.travel.business.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.R;
import com.zhuandian.travel.activity.NewTravelGuideActivity;
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


/**
 * desc :
 * author：xiedong
 * date：2020/03/21
 */
public class TravelGuideFragment extends BaseFragment {

    @BindView(R.id.brv_list)
    BaseRecyclerView brvGoodsList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private List<TravelGuideEntity> mDatas = new ArrayList<>();
    private TravelGuideAdapter travelGuideAdapter;
    private int currentCount = -10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_travel_guide;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("旅游攻略");
        travelGuideAdapter = new TravelGuideAdapter(mDatas, actitity);
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


    @OnClick({R.id.tv_new, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new:
                startActivity(new Intent(actitity, NewTravelGuideActivity.class));
                break;
            case R.id.tv_search:
                String title = etKeyword.getText().toString();

                break;
        }
    }


}
