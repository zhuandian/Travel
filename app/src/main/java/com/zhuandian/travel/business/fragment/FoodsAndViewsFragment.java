package com.zhuandian.travel.business.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.R;
import com.zhuandian.travel.adapter.FoodsAndViewsAdapter;
import com.zhuandian.travel.business.utils.BaseRecyclerView;
import com.zhuandian.travel.entity.FoodsAndViewsEntity;
import com.zhuandian.travel.entity.SendHelpEntity;
import com.zhuandian.travel.entity.UserEntity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

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
        handleHelperEvent();
    }

    private void handleHelperEvent() {
        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (userEntity.getType() != 1)
            return;

        BmobQuery<SendHelpEntity> query = new BmobQuery<>();
        query.addWhereNotEqualTo("helpState", 1);
        query.include("helperUser");
//        query.addWhereEqualTo("helperLocal", userEntity.getViewsLocal());
        query.findObjects(new FindListener<SendHelpEntity>() {
            @Override
            public void done(List<SendHelpEntity> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    SendHelpEntity helpEntity = list.get(0);
                    if (helpEntity.getHelperLevel() == 1 || userEntity.getViewsLocal().equals(helpEntity.getHelperLocal())) {
                        new AlertDialog.Builder(actitity)
                                .setTitle("有人呼救")
                                .setMessage("呼救人：\n" + helpEntity.getHelperUser().getUsername() + "\n地点：" + helpEntity.getHelperLocal() + "\n呼救等级:" + helpEntity.getHelperLevel())
                                .setNegativeButton("去营救", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        helpEntity.setHelpState(1);
                                        helpEntity.update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(actitity, "请立即前往救助...", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }).show();
                    }
                }
            }
        });
    }

    private void initRefreshListener() {
        brvGoodsList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                loastAndFoundAdapter.notifyDataSetChanged();
                loadDatas();
                handleHelperEvent();
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
