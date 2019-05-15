package com.example.teacher_everywhere.view.my;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.LikeBean;

public interface LikeView extends BaseMvpView {
    void onSuccess(LikeBean.ResultEntity resultEntity);
    void onFail(String msg);
}
