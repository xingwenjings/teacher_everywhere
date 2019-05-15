package com.example.teacher_everywhere.presenter;


import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.VerifyCodeBean;
import com.example.teacher_everywhere.model.LoginModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.LoginView;

/**
 * @author xts
 *         Created by asus on 2019/4/29.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);
    }

    public void getVerifyCode() {
        mLoginModel.getVerifyCode(new ResultCallBack<VerifyCodeBean>() {
            @Override
            public void onSuccess(VerifyCodeBean bean) {

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
