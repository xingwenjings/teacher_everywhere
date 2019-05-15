package com.example.teacher_everywhere.view.my;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.BanmiBean;

public interface FollowView extends BaseMvpView {
    void setSuccess(BanmiBean.ResultEntity resultEntity);
    void setField(String msg);
}
