package com.example.teacher_everywhere.model;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.BundleBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;
import com.example.teacher_everywhere.util.SpUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SubjectModel extends BaseModel {
    public void getBundleData(final ResultCallBack<BundleBean> resultCallBack) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.getBundle((String) SpUtil.getParam(Constants.TOKEN,""))
                .compose(RxUtils.<BundleBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BundleBean>() {
                    @Override
                    public void onNext(BundleBean bundleBean) {
                        if (bundleBean!=null){
                            if (bundleBean.getCode()==EveryWhereService.SUCCESS_CODE){
                                resultCallBack.onSuccess(bundleBean);
                            }else {
                                resultCallBack.onFail(bundleBean.getDesc());
                            }
                        }
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
