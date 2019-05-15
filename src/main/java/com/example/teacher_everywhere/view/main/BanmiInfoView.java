package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.BanmiInfo;

public interface BanmiInfoView extends BaseMvpView {
    void onBanmiXiangSuccess(BanmiInfo resultEntity);
    void onBanmiXiangField(String msg);
}
