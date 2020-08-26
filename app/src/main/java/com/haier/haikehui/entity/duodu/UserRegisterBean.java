package com.haier.haikehui.entity.duodu;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/26 9:20 AM
 * desc   : 用户登记
 * version: 1.0
 */
public class UserRegisterBean {

    private String deviceCardId;
    private String userId;
    private String cardId;

    public String getDeviceCardId() {
        return deviceCardId;
    }

    public void setDeviceCardId(String deviceCardId) {
        this.deviceCardId = deviceCardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
