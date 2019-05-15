package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseApp;
import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.VerifyCodeBean;
import com.example.teacher_everywhere.model.LoginModel;
import com.example.teacher_everywhere.net.ApiService;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.VerifyView;

public class VerifyPresenter extends BasePresenter<VerifyView> {

    private LoginModel loginModel;

    @Override
    protected void initModel() {
        loginModel = new LoginModel();
        mModels.add(loginModel);
    }

    public void getVerifyCode() {
        loginModel.getVerifyCode(new ResultCallBack<VerifyCodeBean>() {
            @Override
            public void onSuccess(VerifyCodeBean bean) {
                if (bean!=null&&bean.getCode()==ApiService.SUCCESS_CODE){
                    if (mMvpView!=null){
                        mMvpView.setData(bean.getData());
                    }
                }else {
                    if (mMvpView!=null){
                        mMvpView.toastShort(BaseApp.getRes().getString(R.string.get_verify_faild));
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                mMvpView.toastShort("失败"+msg);
            }
        });
    }
}
