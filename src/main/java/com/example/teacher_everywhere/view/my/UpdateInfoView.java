package com.example.teacher_everywhere.view.my;


import com.example.teacher_everywhere.base.BaseMvpView;

/**
 * @author xts
 *         Created by asus on 2019/4/29.
 */

public interface UpdateInfoView extends BaseMvpView {
    void onSuccess(String msg);
    void onFail(String msg);
}
