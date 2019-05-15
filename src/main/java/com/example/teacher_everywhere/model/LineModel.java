package com.example.teacher_everywhere.model;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.MainDataBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LineModel extends BaseModel {

    public void getLineData(String param, int id, int i, final ResultCallBack<MainDataBean> resultCallBack) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.getLineInfo(param,id,i)
                .compose(RxUtils.<MainDataBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<MainDataBean>() {
                    @Override
                    public void onNext(MainDataBean mainDataBean) {
                        if (mainDataBean!=null){
                            if (mainDataBean.getCode()==EveryWhereService.SUCCESS_CODE){
                                resultCallBack.onSuccess(mainDataBean);
                            }else {
                                resultCallBack.onFail(mainDataBean.getDesc());
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
