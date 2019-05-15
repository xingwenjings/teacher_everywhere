package com.example.teacher_everywhere.model;

import android.util.Log;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;
import com.example.teacher_everywhere.util.SpUtil;

import io.reactivex.disposables.Disposable;
//评价
public class ReViewsModel extends BaseModel {
    private String TAG="ReViewsModel";

    public void getReviews(int id, int page, final ResultCallBack<MainDataInfo> callBack){
        HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL,EveryWhereService.class)
                .getReviews((String) SpUtil.getParam(Constants.TOKEN,""),id,page)
                .compose(RxUtils.<MainDataInfo>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<MainDataInfo>() {
                    @Override
                    public void error(String msg) {
                        Log.e(TAG, "error: e=" + msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MainDataInfo mainDataInfo) {
                        if (mainDataInfo != null){
                            if (mainDataInfo.getCode() == EveryWhereService.SUCCESS_CODE){
                                callBack.onSuccess(mainDataInfo);
                            }else {
                                callBack.onFail(mainDataInfo.getDesc());
                            }
                        }
                    }
                });
    }
}
