package com.zhuandian.travel.business.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.entity.TravelTeamEntity;
import com.zhuandian.travel.entity.UserEntity;
import com.zhuandian.travel.entity.ViewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class NewTravelTeamActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.fl_local)
    RadioGroup flLocal;
    private String viewsLocal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_travel_team;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("新建旅游团");
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
                        RadioButton radioButton = new RadioButton(NewTravelTeamActivity.this);
                        radioButton.setText(list.get(i).getName());
                        int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewsLocal=list.get(finalI).getName();

                            }
                        });
                        flLocal.addView(radioButton);
                    }

                }
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_new})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_new:
                createNew();
                break;
        }
    }

    private void createNew() {
        String content = etContent.getText().toString();
        String title = etTitle.getText().toString();

        if (TextUtils.isEmpty(content)||TextUtils.isEmpty(title)||TextUtils.isEmpty(viewsLocal)){
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        TravelTeamEntity travelTeamEntity = new TravelTeamEntity();
        travelTeamEntity.setTitle(title);
        travelTeamEntity.setContent(content);
        travelTeamEntity.setLocal(viewsLocal);
        travelTeamEntity.setUserEntity(BmobUser.getCurrentUser(UserEntity.class));
        travelTeamEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Toast.makeText(NewTravelTeamActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

}
