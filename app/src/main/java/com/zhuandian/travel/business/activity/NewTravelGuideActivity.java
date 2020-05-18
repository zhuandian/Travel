package com.zhuandian.travel.business.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.utils.PictureSelectorUtils;
import com.zhuandian.travel.entity.ThemeEntity;
import com.zhuandian.travel.entity.TravelGuideEntity;
import com.zhuandian.travel.entity.ViewsEntity;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class NewTravelGuideActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_views_local)
    TextView tvViewsLocal;
    @BindView(R.id.tv_views_theme)
    TextView tvViewsTheme;
    @BindView(R.id.tv_time_level)
    TextView tvTimeLevel;
    @BindView(R.id.tv_money_level)
    TextView tvMoneyLevel;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    private String imgUrl;
    private String viewsLocal;
    private String viewsTheme;
    private int timeLevel = -1;
    private int moneyLevel = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_travel_guide;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("发布攻略");
    }


    @OnClick({R.id.iv_back, R.id.iv_img, R.id.tv_views_local, R.id.tv_views_theme, R.id.tv_time_level, R.id.tv_money_level, R.id.tv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_img:
                PictureSelectorUtils.selectImg(PictureSelector.create(this), 1);
                break;
            case R.id.tv_views_local:
                showViewsLocalDialog();
                break;
            case R.id.tv_views_theme:
                showViewsThemeDialog();
                break;
            case R.id.tv_time_level:
                showTimeLevelDialog();
                break;
            case R.id.tv_money_level:
                showMoneyLevel();
                break;
            case R.id.tv_release:
                releaseNewTravelGuide();
                break;
        }
    }

    private void releaseNewTravelGuide() {
        String content = etContent.getText().toString();
        String name = etName.getText().toString();

        if (TextUtils.isEmpty(viewsLocal) || TextUtils.isEmpty(viewsTheme) || TextUtils.isEmpty(imgUrl) || TextUtils.isEmpty(viewsTheme) ||
                TextUtils.isEmpty(viewsLocal) || timeLevel == -1 || moneyLevel == -1) {
            Toast.makeText(this, "请输入所有发布项...", Toast.LENGTH_SHORT).show();
            return;
        }
        TravelGuideEntity travelGuideEntity = new TravelGuideEntity();
        travelGuideEntity.setContent(content);
        travelGuideEntity.setTitle(name);
        travelGuideEntity.setImgUrl(imgUrl);
        travelGuideEntity.setMoneyLevel(moneyLevel);
        travelGuideEntity.setTimeLevel(timeLevel);
        travelGuideEntity.setViewsLocal(viewsLocal);
        travelGuideEntity.setViewsTheme(viewsTheme);
        travelGuideEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(NewTravelGuideActivity.this, "发布成功...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void showMoneyLevel() {
        String[] items = new String[]{"一级", "二级", "三级"};

        new AlertDialog.Builder(NewTravelGuideActivity.this)
                .setTitle("选择费用等级")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moneyLevel = which;
                        tvMoneyLevel.setText(tvMoneyLevel.getText() + ":" + items[which]);
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showTimeLevelDialog() {
        String[] items = new String[]{"一级", "二级", "三级"};

        new AlertDialog.Builder(NewTravelGuideActivity.this)
                .setTitle("选择时间等级")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeLevel = which;
                        tvTimeLevel.setText(tvTimeLevel.getText() + ":" + items[which]);
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showViewsThemeDialog() {
        BmobQuery<ThemeEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ThemeEntity>() {
            @Override
            public void done(List<ThemeEntity> list, BmobException e) {

                String[] items = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    items[i] = list.get(i).getName();
                }
                new AlertDialog.Builder(NewTravelGuideActivity.this)
                        .setTitle("选择主题")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewsTheme = items[which];
                                tvViewsTheme.setText(items[which]);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void showViewsLocalDialog() {
        BmobQuery<ViewsEntity> query = new BmobQuery<>();
        query.findObjects(new FindListener<ViewsEntity>() {
            @Override
            public void done(List<ViewsEntity> list, BmobException e) {

                String[] items = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    items[i] = list.get(i).getName();
                }
                new AlertDialog.Builder(NewTravelGuideActivity.this)
                        .setTitle("选择地点")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewsLocal = items[which];
                                tvViewsLocal.setText(items[which]);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    String imagePath = selectList.get(0).getCompressPath();

                    BmobFile bmobFile = new BmobFile(new File(imagePath));
                    bmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                imgUrl = bmobFile.getFileUrl();
                                decodePath2Bitmap(imagePath);
                            }
                        }
                    });
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
            ivImg.setImageBitmap(bm);
        }
    }

}
