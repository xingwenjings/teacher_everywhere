package com.example.teacher_everywhere.view.main;

import android.app.Activity;

import com.example.teacher_everywhere.base.BaseMvpView;

public interface LoginOrBindView extends BaseMvpView {
    String getPhone();
    Activity getAct();

    void go2MainActivity();

    void setCode(String code);
}
