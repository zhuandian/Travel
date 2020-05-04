package com.zhuandian.travel;


import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.adapter.HomePageAdapter;
import com.zhuandian.travel.business.fragment.TravelDiaryFragment;
import com.zhuandian.travel.business.fragment.TravelGuideFragment;
import com.zhuandian.travel.business.fragment.FoodsAndViewsFragment;
import com.zhuandian.travel.business.fragment.MineFragment;
import com.zhuandian.travel.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tab_bottom)
    BottomNavigationView tabBottom;
    public static final int PAGE_LOST = 0;
    public static final int PAGE_FOUND = 1;
    public static final int PAGE_TRAVEL = 2;
    public static final int PAGE_MY = 3;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {

        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FoodsAndViewsFragment());
        fragmentList.add(new TravelGuideFragment());
        if (userEntity.getType()!=1){
            fragmentList.add(new TravelDiaryFragment());
        }
        fragmentList.add(new MineFragment());
        vpHome.setAdapter(new HomePageAdapter(getSupportFragmentManager(), fragmentList));
        vpHome.setOffscreenPageLimit(4);

        vpHome.setCurrentItem(PAGE_LOST);
        initBottomTab();
        if (userEntity.getType()==1){
            tabBottom.getMenu().getItem(2).setVisible(false);
        }

    }

    public void setCurrentPage(int position) {
        vpHome.setCurrentItem(position);
    }

    private void initBottomTab() {
        vpHome.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabBottom.getMenu().getItem(position).setChecked(true);
            }
        });

        tabBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab_lost:
                        vpHome.setCurrentItem(PAGE_LOST);
                        break;
                    case R.id.tab_found:
                        vpHome.setCurrentItem(PAGE_FOUND);
                        break;
                    case R.id.tab_my:
                        vpHome.setCurrentItem(PAGE_MY);
                        break;
                    case R.id.tab_travel:
                        vpHome.setCurrentItem(PAGE_TRAVEL);
                        break;
                }

                return true;
            }
        });
    }
}
