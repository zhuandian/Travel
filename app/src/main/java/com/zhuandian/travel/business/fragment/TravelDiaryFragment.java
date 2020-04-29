package com.zhuandian.travel.business.fragment;

import android.view.View;
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
 */
public class TravelDiaryFragment extends BaseFragment {

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
//    private List<BookEntity> mDatas = new ArrayList<>();
//    private BookAdapter loastAndFoundAdapter;
//    private int currentCount = -10;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_travel_diary;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("旅游日记");
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
//        BmobQuery<BookEntity> query = new BmobQuery<>();
//        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
//        query.order("-updatedAt");
//        query.include("userEntity");
//        query.setLimit(10);
//        query.setSkip(currentCount);
//        query.findObjects(new FindListener<BookEntity>() {
//            @Override
//            public void done(List<BookEntity> list, BmobException e) {
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

    @OnClick(R.id.tv_new)
    public void onViewClicked() {
//        startActivity(new Intent(actitity, ReleaseBookActivity.class));
    }


}
