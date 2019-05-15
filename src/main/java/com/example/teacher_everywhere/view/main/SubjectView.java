package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.BundleBean;

public interface SubjectView extends BaseMvpView {
    void onBundleSuccess(BundleBean.ResultBean resultBean);
    void onBundleField(String msg);
}
