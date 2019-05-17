package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.VersionBean;

public interface MainVersionView extends BaseMvpView {
    void setOnsuccess(VersionBean.ResultBean resultBean);
    void setField(String error);
}
