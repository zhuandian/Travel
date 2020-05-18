package com.zhuandian.travel.business.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.travel.R;
import com.zhuandian.travel.business.utils.PictureSelectorUtils;
import com.zhuandian.travel.entity.TravelDiaryEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class NewTravelDiaryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_priview_img_1)
    ImageView ivPriviewImg1;
    @BindView(R.id.iv_priview_img_2)
    ImageView ivPriviewImg2;
    @BindView(R.id.iv_priview_img_3)
    ImageView ivPriviewImg3;
    @BindView(R.id.ll_photo_container)
    LinearLayout llPhotoContainer;
    @BindView(R.id.ll_photo_preview)
    LinearLayout llPhotoPreview;
    @BindView(R.id.rl_image_container)
    RelativeLayout rlImageContainer;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_release)
    TextView tvRelease;
    private ImageView[] previewImg;//上传图片本地预览视图
    private List<String> imgUrls = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_travel_diary;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("发布游记");
        previewImg = new ImageView[]{ivPriviewImg1, ivPriviewImg2, ivPriviewImg3};
    }



    @OnClick({R.id.iv_back, R.id.ll_photo_preview, R.id.tv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_photo_preview:
                PictureSelectorUtils.selectImg(PictureSelector.create(this), 3);
                break;
            case R.id.tv_release:
                newRelease();
                break;
        }
    }

    private void newRelease() {
        String content = etContent.getText().toString();
        String name = etName.getText().toString();
        if (TextUtils.isEmpty(content)||TextUtils.isEmpty(name)||imgUrls.size()==0){
            Toast.makeText(this, "请完善所有信息", Toast.LENGTH_SHORT).show();
            return;
        }

        TravelDiaryEntity travelDiaryEntity = new TravelDiaryEntity();
        travelDiaryEntity.setTitle(name);
        travelDiaryEntity.setContent(content);
        travelDiaryEntity.setImgUrls(imgUrls);
        travelDiaryEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Toast.makeText(NewTravelDiaryActivity.this, "发布成功...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                llPhotoPreview.setVisibility(View.GONE); //隐藏上传商品图片提示
                String[] imgs = new String[selectList.size()];
                for (int i = 0; i < selectList.size(); i++) {
                    imgs[i]= selectList.get(i).getPath();
                    previewImg[i].setImageURI(Uri.parse(selectList.get(i).getPath()));
                }

                BmobFile.uploadBatch(imgs, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> urls) {
                        imgUrls = urls;
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {
                        System.out.println(s+"-----------------------");
                    }
                });
            }

        }
    }
}
