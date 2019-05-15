package com.example.teacher_everywhere.model;


import android.util.Log;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.MainDataBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 灵风 on 2019/5/5.
 */

public class MainModel extends BaseModel {
    public void getMainData(int page, String token, final ResultCallBack<MainDataBean> callBack){
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        Observable<MainDataBean> observable = service.getMainData(page, token);
        observable.compose(RxUtils.<MainDataBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<MainDataBean>() {
                    @Override
                    public void error(String msg) {
                        callBack.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MainDataBean mainDataBean) {
                        if (mainDataBean != null){
                            if (mainDataBean.getCode() == EveryWhereService.SUCCESS_CODE){
                                callBack.onSuccess(mainDataBean);
                            }else {
                                callBack.onFail(mainDataBean.getDesc());
                            }
                        }
                    }
                });
    }


}
