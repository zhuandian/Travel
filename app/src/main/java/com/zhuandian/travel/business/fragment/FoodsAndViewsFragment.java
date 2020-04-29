package com.zhuandian.travel.business.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.R;
import com.zhuandian.travel.adapter.FoodsAndViewsAdapter;
import com.zhuandian.travel.business.utils.BaseRecyclerView;
import com.zhuandian.travel.entity.FoodsAndViewsEntity;


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
public class FoodsAndViewsFragment extends BaseFragment {
    @BindView(R.id.brv_list)
    BaseRecyclerView brvGoodsList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;


    private List<FoodsAndViewsEntity> mDatas = new ArrayList<>();
    private FoodsAndViewsAdapter loastAndFoundAdapter;
    private int currentCount = -10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_foods_and_views;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("美食美景");
        loastAndFoundAdapter = new FoodsAndViewsAdapter(mDatas, actitity);
        brvGoodsList.setRecyclerViewAdapter(loastAndFoundAdapter);
        loadDatas();
        initRefreshListener();
    }


    private void initRefreshListener() {
        brvGoodsList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                loastAndFoundAdapter.notifyDataSetChanged();
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
        BmobQuery<FoodsAndViewsEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");

        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<FoodsAndViewsEntity>() {
            @Override
            public void done(List<FoodsAndViewsEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    loastAndFoundAdapter.notifyDataSetChanged();
                    brvGoodsList.setRefreshLayoutState(false);
                } else {
                    brvGoodsList.setRefreshLayoutState(false);
                }
            }
        });
    }


}
