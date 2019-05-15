package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.MainDataInfo;

public interface MainInfoView extends BaseMvpView {
    void updateUi(MainDataInfo.ResultEntity entity);
    void onSuccess(String msg);
    void onFail(String msg);
}
