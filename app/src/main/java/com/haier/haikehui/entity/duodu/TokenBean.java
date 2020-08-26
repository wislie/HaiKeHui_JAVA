package com.haier.haikehui.entity.duodu;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 3:41 PM
 * desc   : token实体对象
 * version: 1.0
 */
public class TokenBean {
    private String agentId;
    private String appId;
    private String expireAt;
    private String token;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
