package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.BanmiBean;

public interface BanMiView extends BaseMvpView {
    void onSuccess(BanmiBean.ResultEntity entity);
    void onFail(String msg);

    void toastShort(String msg);
}
