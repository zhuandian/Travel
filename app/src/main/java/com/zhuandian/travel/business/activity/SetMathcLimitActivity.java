package com.zhuandian.travel.business.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.UserEntity;
import com.zhuandian.travel.entity.ViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class SetMathcLimitActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.fl_local)
    RadioGroup flLocal;
    private String macthcLocal;
    private String userGender;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_mathc_limit;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("设置筛选条件");
        tvRight.setText("确定");
        tvRight.setVisibility(View.VISIBLE);
        initLocal();
    }


    private void initLocal() {
        BmobQuery<ViewsEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ViewsEntity>() {
            @Override
            public void done(List<ViewsEntity> list, BmobException e) {
                if (e == null) {
                    flLocal.removeAllViews();
                    for (int i = 0; i < list.size(); i++) {
                        RadioButton radioButton = new RadioButton(SetMathcLimitActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                macthcLocal = list.get(finalI).getName();

                            }
                        });
                        flLocal.addView(radioButton);
                    }

                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.rb_woman, R.id.rb_man, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rb_woman:
                userGender = "女";
                break;
            case R.id.rb_man:
                userGender = "男";
                break;
            case R.id.tv_right:
                setUserLimit();
                break;
        }
    }

    private void setUserLimit() {
        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (TextUtils.isEmpty(userGender) || TextUtils.isEmpty(macthcLocal)) {
            Toast.makeText(this, "请设置条件", Toast.LENGTH_SHORT).show();
            return;
        }

        userEntity.setGender(userGender);
        userEntity.setMatchLocal(macthcLocal);
        userEntity.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(SetMathcLimitActivity.this, "设置成功，去匹配旅友吧", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
