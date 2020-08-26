package com.haier.haikehui.ui.duodu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.haier.haikehui.R;
import com.haier.haikehui.base.BaseActivity;
import com.haier.haikehui.entity.duodu.BuildingBean;
import com.haier.haikehui.entity.duodu.EstateBean;
import com.haier.haikehui.entity.duodu.RoomBean;
import com.haier.haikehui.entity.duodu.TokenBean;
import com.haier.haikehui.entity.duodu.UnitBean;
import com.haier.haikehui.entity.duodu.UserRegisterBean;
import com.haier.haikehui.presenter.duodu.DuoDuPresenter;
import com.haier.haikehui.util.Constant;
import com.haier.util.ApplicationInfoUtil;
import com.haier.util.FileUtils;
import com.haier.util.Prefs;

import java.io.File;
import java.util.List;

import butterknife.OnClick;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 11:02 PM
 * desc   : 多度activity
 * version: 1.0
 */
public class DuoDuActivity extends BaseActivity<DuoDuPresenter> implements DuoDuPresenter.DuoDuView {

    //需要设置接口请求的限制

    private String appId;
    private String secretKey;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_duodu;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        appId = ApplicationInfoUtil.readMetaDataIntValue(this, "com.haier.haikehui.duodu.appKey");
        secretKey = ApplicationInfoUtil.readMetaDataStringValue(this, "com.haier.haikehui.duodu.secretKey");
        Log.i(TAG, "initView: appId=" + appId + " secretKey=" + secretKey);
    }

    @Override
    protected DuoDuPresenter initPresenter() {
        return new DuoDuPresenter(this);
    }

    @Override
    public void getTokenSuccess(TokenBean tokenBean) {
        Log.i(TAG, "agentId=" + tokenBean.getAgentId() + " token=" + tokenBean.getToken());

        Prefs.with(this).write(Constant.TOKEN, tokenBean.getToken());
        Prefs.with(this).write(Constant.AGENT_ID, tokenBean.getAgentId());

    }

    @Override
    public void getEstateListSuccess(List<EstateBean> estateBeanList) {
        Log.i(TAG, "getEstateListSuccess: " + estateBeanList);
    }

    @Override
    public void getBuildingListSuccess(List<BuildingBean> buildingBeanList) {
        Log.i(TAG, "getBuildingListSuccess: " + buildingBeanList);
    }

    @Override
    public void getUniListSuccess(List<UnitBean> unitBeanList) {
        Log.i(TAG, "getUniListSuccess: " + unitBeanList);
    }

    @Override
    public void getRoomListSuccess(List<RoomBean> roomBeanList) {
        Log.i(TAG, "getRoomListSuccess: " + roomBeanList);
    }

    @Override
    public void tokenUploadFileSuccess(String path) {
        Log.i(TAG, "tokenUploadFileSuccess: " + path);
    }

    @Override
    public void userRegisterThirdSuccess(UserRegisterBean userRegisterBean) {
        Log.i(TAG, "userRegisterThirdSuccess: " + userRegisterBean);
    }

    @OnClick({R.id.btn_click, R.id.btn_estate, R.id.btn_building, R.id.btn_unit, R.id.btn_room, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                presenter.getToken(appId, secretKey);
                break;
            case R.id.btn_estate:
                String token = Prefs.with(this).read(Constant.TOKEN);
                String agentId = Prefs.with(this).read(Constant.AGENT_ID);
                presenter.getEstateList(token, agentId);
                break;
            case R.id.btn_building:
                String token1 = Prefs.with(this).read(Constant.TOKEN);
                presenter.getBuildingList(token1, "9403");
                break;
            case R.id.btn_unit:
                String token2 = Prefs.with(this).read(Constant.TOKEN);
                presenter.getUnitList(token2, "91746");
                break;
            case R.id.btn_room:
                String token3 = Prefs.with(this).read(Constant.TOKEN);
                presenter.getRoomList(token3, "9403", 10, 1);
                break;
            case R.id.btn_upload:

                checkPermission();

                //rxpermission 请求

                break;
        }
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //开启权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            openPic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openPic();
                } else {
                    Toast.makeText(this, "请求权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openPic() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                Uri uri = data.getData();
                String path = FileUtils.getRealPathFromURI(this, uri);


                File file = new File(path);
                Log.i(TAG, "onActivityResult: path=" + path + " exist=" + file.exists());
                String token4 = Prefs.with(this).read(Constant.TOKEN);
                presenter.tokenUploadFile(token4, path);
                break;
        }
    }
}
