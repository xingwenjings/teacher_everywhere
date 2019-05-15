package com.example.teacher_everywhere.base;

public interface BaseMvpView {
    //显示加载loading的方法
    void showLoading();
    //隐藏加载loading的方法
    void hideLoading();

    void toastShort(String msg);

}
