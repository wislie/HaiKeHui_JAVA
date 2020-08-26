package com.haier.haikehui.base;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 11:41 AM
 * desc   : view最基本的接口
 * version: 1.0
 */
public interface BaseView {

    void showLoadingDialog(String msg);

    void dismissLoadingDialog();
}
