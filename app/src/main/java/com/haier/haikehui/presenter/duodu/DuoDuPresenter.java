package com.haier.haikehui.presenter.duodu;


import com.haier.haikehui.base.BasePresenter;
import com.haier.haikehui.base.BaseView;
import com.haier.haikehui.base.Response;
import com.haier.haikehui.entity.duodu.BuildingBean;
import com.haier.haikehui.entity.duodu.EstateBean;
import com.haier.haikehui.entity.duodu.RoomBean;
import com.haier.haikehui.entity.duodu.TokenBean;
import com.haier.haikehui.entity.duodu.UnitBean;
import com.haier.haikehui.entity.duodu.UserRegisterBean;
import com.haier.haikehui.model.duodu.DuoDuModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 10:45 PM
 * desc   : 多度presenter
 * version: 1.0
 */
public class DuoDuPresenter extends BasePresenter<DuoDuPresenter.DuoDuView> {

    private DuoDuView duoDuView;
    private DuoDuModel duoDuModel;

    public DuoDuPresenter(DuoDuView view) {
        super(view);
        this.duoDuView = view;
        this.duoDuModel = new DuoDuModel();
    }

    /**
     * 获取token
     *
     * @param appId
     * @param secretKey
     */
    public void getToken(String appId, String secretKey) {

        DisposableObserver<Response<TokenBean>> observer = createDisposableObserver(new ResponseCallback<Response<TokenBean>>() {

            @Override
            public void onSuccess(Response<TokenBean> response) {
                if (duoDuView != null) {
                    duoDuView.getTokenSuccess(response.getData());
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        duoDuModel.getToken(appId, secretKey, observer);
    }

    /**
     * 获取小区列表
     *
     * @param token
     * @param agentId
     */
    public void getEstateList(String token, String agentId) {
        DisposableObserver<Response<List<EstateBean>>> observer = createDisposableObserver(new ResponseCallback<Response<List<EstateBean>>>() {

            @Override
            public void onSuccess(Response<List<EstateBean>> response) {
                if (duoDuView != null) {
                    duoDuView.getEstateListSuccess(response.getData());
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        duoDuModel.getEstateList(token, agentId, observer);
    }

    /**
     * 获取栋列表
     *
     * @param token
     * @param depId
     */
    public void getBuildingList(String token, String depId) {
        DisposableObserver<Response<List<BuildingBean>>> observer = createDisposableObserver(new ResponseCallback<Response<List<BuildingBean>>>() {

            @Override
            public void onSuccess(Response<List<BuildingBean>> response) {
                if (duoDuView != null) {
                    duoDuView.getBuildingListSuccess(response.getData());
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        duoDuModel.getBuildingList(token, depId, observer);
    }

    /**
     * 获取单元列表
     *
     * @param token
     * @param buildingId
     */
    public void getUnitList(String token, String buildingId) {
        DisposableObserver<Response<List<UnitBean>>> observer = createDisposableObserver(new ResponseCallback<Response<List<UnitBean>>>() {

            @Override
            public void onSuccess(Response<List<UnitBean>> response) {
                if (duoDuView != null) {
                    duoDuView.getUniListSuccess(response.getData());
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        duoDuModel.getUnitList(token, buildingId, observer);
    }


    /**
     * 获取房间列表
     * @param token
     * @param depId
     * @param limit
     * @param page
     */
    public void getRoomList(String token, String depId, Integer limit, Integer page) {
        DisposableObserver<Response<List<RoomBean>>> observer = createDisposableObserver(new ResponseCallback<Response<List<RoomBean>>>() {

            @Override
            public void onSuccess(Response<List<RoomBean>> response) {
                if (duoDuView != null) {
                    duoDuView.getRoomListSuccess(response.getData());
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
        duoDuModel.getRoomList(token, depId, limit, page, observer);
    }

    /**
     * 上传文件
     * @param token
     * @param filePath
     */
    public void tokenUploadFile(String token, String filePath) {

        Observer<ResponseBody> observer = createObserver(new ResponseCallback<ResponseBody>(){

            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    String str =  body.string();
                    JSONObject jsonObject = new JSONObject(str);
                    String code = jsonObject.getString("code");
                    if("200".equals(code)){
                        //路径
                        String path = jsonObject.getString("data");
                        if(duoDuView!=null){
                            duoDuView.tokenUploadFileSuccess(path);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });

        duoDuModel.tokenUploadFile(token, "imgFile", filePath, observer);

    }

    /**
     * 用户登记
     */
    public  void userRegisterThird(){
        DisposableObserver<Response<UserRegisterBean>> observer = createDisposableObserver(new ResponseCallback<Response<UserRegisterBean>>() {

            @Override
            public void onSuccess(Response<UserRegisterBean> response) {
                if (duoDuView != null) {
                    duoDuView.userRegisterThirdSuccess(response.getData());
                }
            }

            @Override
            public void onFailed(String code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
//        duoDuModel.userRegisterThird();
    }


    public interface DuoDuView extends BaseView {
        //获取token
        void getTokenSuccess(TokenBean tokenBean);

        //小区列表
        void getEstateListSuccess(List<EstateBean> estateBeanList);

        //栋列表
        void getBuildingListSuccess(List<BuildingBean> buildingBeanList);

        //单元列表
        void getUniListSuccess(List<UnitBean> unitBeanList);

        //房间列表
        void getRoomListSuccess(List<RoomBean> roomBeanList);
        //文件上传成功
        void tokenUploadFileSuccess(String path);

        //用户注册成功
        void userRegisterThirdSuccess(UserRegisterBean userRegisterBean);
    }


}
