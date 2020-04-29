package com.zhuandian.travel.business.fragment;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.utils.BaseRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;


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
//    private List<LostAndFoundEntity> mDatas = new ArrayList<>();
//    private LoastAndFoundAdapter loastAndFoundAdapter;
//    private int currentCount = -10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_travel_guide;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("旅游攻略");
//        loastAndFoundAdapter = new LoastAndFoundAdapter(mDatas, actitity);
//        brvGoodsList.setRecyclerViewAdapter(loastAndFoundAdapter);
//        loadDatas();
//        initRefreshListener();
    }


    private void initRefreshListener() {
        brvGoodsList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                currentCount = -10; //重新置位
//                mDatas.clear();
//                loastAndFoundAdapter.notifyDataSetChanged();
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
//        currentCount = currentCount + 10;
//        BmobQuery<LostAndFoundEntity> query = new BmobQuery<>();
//        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
//        query.order("-updatedAt");
//        query.addWhereEqualTo("type", 2);
//        query.setLimit(10);
//        query.setSkip(currentCount);
//        query.findObjects(new FindListener<LostAndFoundEntity>() {
//            @Override
//            public void done(List<LostAndFoundEntity> list, BmobException e) {
//                if (e == null) {
//                    for (int i = 0; i < list.size(); i++) {
//                        mDatas.add(list.get(i));
//                    }
//                    loastAndFoundAdapter.notifyDataSetChanged();
//                    brvGoodsList.setRefreshLayoutState(false);
//                } else {
//                    brvGoodsList.setRefreshLayoutState(false);
//                }
//            }
//        });
    }


    @OnClick({R.id.tv_new, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new:
//                startActivity(new Intent(actitity, ReleaseLostAndFoundActivity.class));
                break;
            case R.id.tv_search:
                String title = etKeyword.getText().toString();

                break;
        }
    }


}
