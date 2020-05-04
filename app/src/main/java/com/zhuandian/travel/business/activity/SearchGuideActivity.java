package com.zhuandian.travel.business.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.ThemeEntity;
import com.zhuandian.travel.entity.ViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.I;
import cn.bmob.v3.listener.FindListener;

public class SearchGuideActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.fl_local)
    FlexboxLayout flLocal;
    @BindView(R.id.fl_theme)
    FlexboxLayout flTheme;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rb_time_level_1)
    RadioButton rbTimeLevel1;
    @BindView(R.id.rb_time_level_2)
    RadioButton rbTimeLevel2;
    @BindView(R.id.rb_time_level_3)
    RadioButton rbTimeLevel3;
    @BindView(R.id.rb_money_level_1)
    RadioButton rbMoneyLevel1;
    @BindView(R.id.rb_money_level_2)
    RadioButton rbMoneyLevel2;
    @BindView(R.id.rb_money_level_3)
    RadioButton rbMoneyLevel3;
    private String viewsLocal;
    private String viewsTheme;
    private int timeLevel=0;
    private int moneyLevel=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_guide;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("搜索攻略");
        initLocal();
        initTheme();
    }

    private void initTheme() {
        BmobQuery<ThemeEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ThemeEntity>() {
            @Override
            public void done(List<ThemeEntity> list, BmobException e) {
                if (e == null) {
                    flTheme.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        RadioButton radioButton = new RadioButton(SearchGuideActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewsTheme = list.get(finalI).getName();
                            }
                        });
                        flTheme.addView(radioButton);
                    }

                }
            }
        });
    }

    private void initLocal() {
        BmobQuery<ViewsEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ViewsEntity>() {
            @Override
            public void done(List<ViewsEntity> list, BmobException e) {
                if (e == null) {
                    flLocal.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        RadioButton radioButton = new RadioButton(SearchGuideActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewsLocal = list.get(finalI).getName();
                            }
                        });
                        flLocal.addView(radioButton);
                    }

                }
            }
        });
    }





    @OnClick({R.id.iv_back, R.id.rb_time_level_1, R.id.rb_time_level_2, R.id.rb_time_level_3, R.id.rb_money_level_1, R.id.rb_money_level_2, R.id.rb_money_level_3, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rb_time_level_1:
                timeLevel=0;
                break;
            case R.id.rb_time_level_2:
                timeLevel=1;
                break;
            case R.id.rb_time_level_3:
                timeLevel=2;
                break;
            case R.id.rb_money_level_1:
                moneyLevel=0;
                break;
            case R.id.rb_money_level_2:
                moneyLevel=1;
                break;
            case R.id.rb_money_level_3:
                moneyLevel=2;
                break;
            case R.id.tv_search:
                Intent intent = new Intent(this,GuideSearchResultActivity.class);
                intent.putExtra("viewsLocal",viewsLocal);
                intent.putExtra("viewsTheme",viewsTheme);
                intent.putExtra("timeLevel",timeLevel);
                intent.putExtra("moneyLevel",moneyLevel);
                startActivity(intent);
                break;
        }
    }
}
