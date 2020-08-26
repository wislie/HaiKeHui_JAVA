package com.haier.haikehui.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/24 2:02 PM
 * desc   : activity基类
 * version: 1.0
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected String TAG = getClass().getSimpleName();

    private Unbinder unbinder;

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        unbinder = ButterKnife.bind(this);

        initView(savedInstanceState);

        presenter = initPresenter();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
//                UtilHelper.hideKeyboard(ev, view,this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(presenter != null){
            //presenter解绑
            presenter.detach();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected abstract int getContentViewId();

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);


    protected abstract P initPresenter();


    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
