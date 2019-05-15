package com.example.teacher_everywhere.model;

import android.util.Log;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.BanmiInfo;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import io.reactivex.disposables.Disposable;

public class BanmiInfoModel extends BaseModel {
    private static final String TAG = "BanmiInfoModel";

    public void getBanmiInfo(String token, int id, int page, final ResultCallBack<BanmiInfo> callBack){
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.getBanmiInfo(token,id,page)
                .compose(RxUtils.<BanmiInfo>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanmiInfo>() {
                    @Override
                    public void error(String msg) {
                        Log.e(TAG, "error: e="+msg );
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BanmiInfo banmiInfo) {
                        if (banmiInfo != null){
                            if (banmiInfo.getCode() == EveryWhereService.SUCCESS_CODE){
                                callBack.onSuccess(banmiInfo);
                            }else {
                                callBack.onFail(banmiInfo.getDesc());
                            }
                        }
                    }
                });
    }

}
