package com.haier.haikehui.base;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 3:40 PM
 * desc   :
 * version: 1.0
 */
public class Response<T> {

    private String code;
    private T data;
    private String message;
    private String desc;

    private Integer totalCount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
