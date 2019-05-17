package com.example.teacher_everywhere.model;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.VersionBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import io.reactivex.disposables.Disposable;

public class MainVeisionModel extends BaseModel {
    public void getVersionData(String param, final ResultCallBack<VersionBean> resultCallBack) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.getVersion(param)
                .compose(RxUtils.<VersionBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VersionBean>() {
                    @Override
                    public void onNext(VersionBean versionBean) {
                        resultCallBack.onSuccess(versionBean);
                    }

                    @Override
                    public void error(String msg) {
                        resultCallBack.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }
}
