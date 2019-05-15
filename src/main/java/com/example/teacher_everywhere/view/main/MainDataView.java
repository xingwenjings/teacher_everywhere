package com.example.teacher_everywhere.view.main;

import com.example.teacher_everywhere.base.BaseMvpView;
import com.example.teacher_everywhere.bean.MainDataBean;

/**
 * @author xts
 *         Created by asus on 2019/4/29.
 */

public interface MainDataView extends BaseMvpView {
    void onSuccess(MainDataBean.ResultEntity resultEntity);
    void onFail(String msg);



}
