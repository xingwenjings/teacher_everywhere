package com.example.teacher_everywhere.model;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.LoginInfo;
import com.example.teacher_everywhere.bean.VerifyCodeBean;
import com.example.teacher_everywhere.net.ApiService;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Login_Or_BindModel extends BaseModel {
    public void loginSina(String uid, final ResultCallBack<LoginInfo> callBack) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.postWeiboLogin(uid).compose(RxUtils.<LoginInfo>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<LoginInfo>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        if (loginInfo != null){
                            if (loginInfo.getCode() == EveryWhereService.SUCCESS_CODE){
                                callBack.onSuccess(loginInfo);
                            }else {
                                callBack.onFail(loginInfo.getDesc());
                            }
                        }
                    }
                });
    }

    public void getVerifyCode(final ResultCallBack<VerifyCodeBean> callBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.sBaseUrl, ApiService.class);
        final Observable<VerifyCodeBean> verifyCode = apiserver.getVerifyCode();
        verifyCode.compose(RxUtils.<VerifyCodeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VerifyCodeBean>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(VerifyCodeBean verifyCodeBean) {
                        callBack.onSuccess(verifyCodeBean);
                    }
                });
    }
}
