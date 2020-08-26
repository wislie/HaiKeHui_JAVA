package com.haier.haikehui.model.duodu;


import com.haier.haikehui.base.BaseModel;
import com.haier.haikehui.base.Response;
import com.haier.haikehui.entity.duodu.BuildingBean;
import com.haier.haikehui.entity.duodu.EstateBean;
import com.haier.haikehui.entity.duodu.RoomBean;
import com.haier.haikehui.entity.duodu.TokenBean;
import com.haier.haikehui.entity.duodu.UnitBean;
import com.haier.haikehui.entity.duodu.UserRegisterBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.DisposableObserver;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 2:22 PM
 * desc   : 多度的接口请求
 * version: 1.0
 */
public class DuoDuModel extends BaseModel {

    /**
     * 获取token
     *
     * @param appId
     * @param secretKey
     * @param observer
     */
    public void getToken(String appId, String secretKey, DisposableObserver<Response<TokenBean>> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("secretKey", secretKey);
        Observable<Response<TokenBean>> observable = service.getToken(map);
        toSubscribe(observable, observer);
    }

    /**
     * 获取小区列表
     *
     * @param token
     * @param agentId
     * @param observer
     */
    public void getEstateList(String token, String agentId, DisposableObserver<Response<List<EstateBean>>> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("agentId", agentId);
        Observable<Response<List<EstateBean>>> observable = service.getDepList(map);
        toSubscribe(observable, observer);
    }

    /**
     * 获取栋列表
     *
     * @param token
     * @param depId
     * @param observer
     */
    public void getBuildingList(String token, String depId, DisposableObserver<Response<List<BuildingBean>>> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("depId", depId);
        Observable<Response<List<BuildingBean>>> observable = service.getBuildingList(map);
        toSubscribe(observable, observer);
    }

    /**
     * 获取单元
     *
     * @param token
     * @param buildingId
     * @param observer
     */
    public void getUnitList(String token, String buildingId, DisposableObserver<Response<List<UnitBean>>> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("buildingId", buildingId);
        Observable<Response<List<UnitBean>>> observable = service.getUnitList(map);
        toSubscribe(observable, observer);
    }

    /**
     * 获取房间
     *
     * @param token
     * @param depId
     * @param limit
     * @param page
     * @param observer
     */
    public void getRoomList(String token, String depId, Integer limit, Integer page, DisposableObserver<Response<List<RoomBean>>> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("depId", depId);
        map.put("limit", limit);
        map.put("page", page);
        Observable<Response<List<RoomBean>>> observable = service.getRoomList(map);
        toSubscribe(observable, observer); //分页如何做
    }

    /**
     * 文件上传
     *
     * @param token
     * @param fileParam
     * @param filePath
     */
    public void tokenUploadFile(String token, String fileParam, String filePath, Observer<ResponseBody> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);

        RequestBody tokenBody = formToRequestBody(MultipartBody.FORM, token);
        MultipartBody.Part multipartFile = fileToMultipartBody(filePath, fileParam);
        Observable<ResponseBody> observable = service.tokenUploadFile(tokenBody, multipartFile);
        toSubscribe(observable, observer);
    }

    /**
     * 用户登记
     */
    public void userRegisterThird(String token, Integer platId, Integer depId, Integer unitId, Integer roomNumberId,
                                  String nationCode, String mobileNo, String userName, Integer isUserAppFlag,
                                  String isForeign, String personCard, Integer zjzl, String sentGovernment,
                                  String validity, String cardIco, Integer authType, String nation, Integer careType,
                                  Integer deviceTypeId, String cardNo, String expireTimeStr, String beginTimeStr,
                                  String endTimeStr, Integer userFaceFlag, String faceImg, String faceTimeValid,
                                  String faceBeginTimeStr, String faceEndTimeStr, DisposableObserver<Response<UserRegisterBean>> observer) {
        DuoDuApiService service = createService(DuoDuApiService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("platId", 1);//平台 ID'1-物管,2-综合治 理,7-新网智能'
        map.put("depId", depId);
        map.put("unitId", unitId);
        map.put("roomNumberId", roomNumberId);
        map.put("nationCode", nationCode);
        map.put("mobileNo", mobileNo);
        map.put("userName", userName);
        map.put("isUserAppFlag", 1); //是否开启 APP 授权[0:否，1:是]
        map.put("isForeign", 1); //证件是否外籍证件是否外籍[外藉，1 中国大陆，2 中国香港，3 中国澳门，4 中国台湾，5 新加坡]，默认 1
        map.put("personCard", personCard);
        map.put("zjzl", 1); //证件种类[1 身份证号, 2 港澳通行证,3 台胞证, 4 护照]，默认
        map.put("sentGovernment", sentGovernment);
        map.put("validity", validity);
        map.put("cardIco", cardIco); //是否需要完整的路径
        map.put("authType", 2); //用户类型[0-业主,1-家人, 2-租 客, 3-临时客人,4-代理人]，默认 2
        map.put("nation", "汉"); //民族，长度[10]，注意不带族字， 默认汉
        map.put("careType", 1); //关爱人员类型 [1=>普通,2=> 伤残人士,3=>孤寡老人,4=>老 上访户,5=>老干部,6=>留守儿 童,7=>五保户,8=>其他]
        map.put("deviceTypeId", 1);//卡类型[5: ‘IC 卡’;11:’身份证 卡’; 30:’CPU 卡’;24:’蓝牙 设备’;19:其他卡] 默认 1
        map.put("cardNo", cardNo);
        map.put("expireTimeStr", expireTimeStr);
        map.put("beginTimeStr", beginTimeStr);
        map.put("endTimeStr", endTimeStr);
        map.put("userFaceFlag", 1);//是否开启人脸授权【0:否，1:是]
        map.put("faceImg", faceImg); //人脸图片地址
        map.put("faceTimeValid", faceTimeValid);
        map.put("faceBeginTimeStr", faceBeginTimeStr);
        map.put("faceEndTimeStr", faceEndTimeStr);
        Observable<Response<UserRegisterBean>> observable = service.userRegisterThird(map);
        toSubscribe(observable, observer);
    }


    interface DuoDuApiService {

        @GET("open_api/auth/getToken/v1")
        Observable<Response<TokenBean>> getToken(@QueryMap Map<String, Object> map); //获取token

        @FormUrlEncoded
        @POST("open_api/h_d/getDepList/v1")
        Observable<Response<List<EstateBean>>> getDepList(@FieldMap Map<String, Object> map); //获取小区列表

        @GET("open_api/h_d/getBuildingList/v1")
        Observable<Response<List<BuildingBean>>> getBuildingList(@QueryMap Map<String, Object> map); //获取栋

        @GET("open_api/h_d/getUnitList/v1")
        Observable<Response<List<UnitBean>>> getUnitList(@QueryMap Map<String, Object> map); //获取单元

        @GET("open_api/h_d/getRoomList/v1")
        Observable<Response<List<RoomBean>>> getRoomList(@QueryMap Map<String, Object> map); //获取房间

        @Multipart
        @POST("tokenUploadFile/v1")
        Observable<ResponseBody> tokenUploadFile(@Part("token") RequestBody token, @Part MultipartBody.Part imgFile); //图片上传

        @FormUrlEncoded
        @POST("open_api/u_o_c/userRegistThird/v1")
        Observable<Response<UserRegisterBean>> userRegisterThird(@FieldMap Map<String, Object> map); //用户登记
    }

}
