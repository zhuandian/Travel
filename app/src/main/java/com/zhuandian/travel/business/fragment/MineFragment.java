package com.zhuandian.travel.business.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.activity.ChoseLocalActivity;
import com.zhuandian.travel.business.activity.FindHelperActivity;
import com.zhuandian.travel.business.activity.SelectFriendActivity;
import com.zhuandian.travel.business.activity.SendHelpActivity;
import com.zhuandian.travel.business.activity.TravelTeamActivity;
import com.zhuandian.travel.business.activity.SetMathcLimitActivity;
import com.zhuandian.travel.business.login.LoginActivity;
import com.zhuandian.travel.entity.SendHelpEntity;
import com.zhuandian.travel.entity.UserEntity;
import com.zhuandian.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * desc :
 * author：xiedong
 * date：2020/03/21
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_local)
    TextView tvLocal;
    @BindView(R.id.tv_find_helper)
    TextView tvFindHelper;
    @BindView(R.id.tv_match_friend)
    TextView tvMatchFriend;
    @BindView(R.id.tv_send_help)
    TextView tvSendHelp;
    private SharedPreferences sharedPreferences;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

        sharedPreferences = actitity.getSharedPreferences("config", Context.MODE_PRIVATE);
        String headerPath = sharedPreferences.getString("header_path", "");
        if (!headerPath.isEmpty()) {
            decodePath2Bitmap(headerPath);
        }
        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (userEntity != null) {
            tvNickName.setText((userEntity.getType() == 1 ? "管理员：" : "普通用户：") + userEntity.getUsername());
            tvPhone.setText(userEntity.getMobilePhoneNumber());
            tvLocal.setText(userEntity.getLocal());
        }


        tvFindHelper.setVisibility(userEntity.getType()!=1?View.VISIBLE: View.GONE);
        tvMatchFriend.setVisibility(userEntity.getType()!=1?View.VISIBLE: View.GONE);
        tvSendHelp.setVisibility(userEntity.getType()!=1?View.VISIBLE: View.GONE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    String imagePath = selectList.get(0).getCompressPath();
                    sharedPreferences = actitity.getSharedPreferences("config", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("header_path", imagePath).commit();
                    decodePath2Bitmap(imagePath);
                }
            }
        }
    }


    /**
     * 把指定路径的image资源转成Bitmap
     *
     * @param path
     */
    private void decodePath2Bitmap(String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        if (bm != null) {
            ivHeader.setImageBitmap(bm);
        }
    }

    @OnClick({R.id.tv_find_helper, R.id.tv_match_friend, R.id.tv_send_help, R.id.tv_logout,R.id.tv_mathc_limit,R.id.tv_travel_team})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_find_helper:
                startActivity(new Intent(actitity, FindHelperActivity.class));
                break;
            case R.id.tv_match_friend:
                startActivity(new Intent(actitity, SelectFriendActivity.class));
                break;
            case R.id.tv_send_help:
                startActivity(new Intent(actitity, SendHelpActivity.class));
                break;
            case R.id.tv_logout:
                startActivity(new Intent(actitity, LoginActivity.class));
                BmobUser.logOut();
                actitity.finish();
                break;
            case R.id.tv_travel_team:
                startActivity(new Intent(actitity, ChoseLocalActivity.class));
                break;
            case R.id.tv_mathc_limit:
                startActivity(new Intent(actitity, SetMathcLimitActivity.class));
                break;
        }
    }
}
