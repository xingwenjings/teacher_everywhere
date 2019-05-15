package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.MainDataInfo;

public interface ReViewsView extends BaseMvpView {
    void onSucessReView(MainDataInfo.ResultEntity resultEntity);
    void onFieldReView(String msg);
}
