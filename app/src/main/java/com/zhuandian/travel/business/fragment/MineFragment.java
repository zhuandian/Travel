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
import com.zhuandian.travel.business.login.LoginActivity;
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
    @BindView(R.id.tv_lost)
    TextView tvLost;
    @BindView(R.id.tv_found)
    TextView tvFound;
    @BindView(R.id.tv_book)
    TextView tvBook;
    @BindView(R.id.tv_more_setting)
    TextView tvMoreSetting;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_manage_user)
    TextView tvManageUser;
    @BindView(R.id.tv_local)
    TextView tvLocal;
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

    }

    @OnClick({R.id.iv_header, R.id.tv_nick_name, R.id.tv_more_setting, R.id.tv_logout, R.id.tv_lost, R.id.tv_found, R.id.tv_book, R.id.tv_manage_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_header:
//                PictureSelectorUtils.selectImg(PictureSelector.create(this), 1);
//                break;
//            case R.id.tv_lost:
//                ((MainActivity) actitity).setCurrentPage(MainActivity.PAGE_LOST);
//                break;
//            case R.id.tv_found:
//                ((MainActivity) actitity).setCurrentPage(MainActivity.PAGE_FOUND);
//                break;
//            case R.id.tv_book:
//                ((MainActivity) actitity).setCurrentPage(MainActivity.PAGE_TRAVEL);
//                break;
//            case R.id.tv_more_setting:
//                startActivity(new Intent(actitity, PersonalDataActivity.class));
//                break;
            case R.id.tv_logout:
                startActivity(new Intent(actitity, LoginActivity.class));
                BmobUser.logOut();
                actitity.finish();
                break;
//            case R.id.tv_manage_user:
//                startActivity(new Intent(actitity, ManageUserActivity.class));
//                break;
        }
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

}
