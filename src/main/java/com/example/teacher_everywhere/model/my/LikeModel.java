package com.example.teacher_everywhere.model.my;

import android.util.Log;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.LikeBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;
import com.example.teacher_everywhere.util.SpUtil;

import io.reactivex.disposables.Disposable;

public class LikeModel extends BaseModel {
    private static final String TAG = "LikePresenter";

    public void getCollectData(int page, final ResultCallBack<LikeBean> callBack) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.getCollectData((String) SpUtil.getParam(Constants.TOKEN, ""), page)
                .compose(RxUtils.<LikeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<LikeBean>() {
                    @Override
                    public void error(String msg) {
                        Log.e(TAG, "error: e=" + msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LikeBean likeBean) {
                        if (likeBean != null) {
                            if (likeBean.getCode() == EveryWhereService.SUCCESS_CODE) {
                                callBack.onSuccess(likeBean);
                                Log.e(TAG,"onsuccess--"+likeBean.getResult().toString());
                            } else {
                                callBack.onFail(likeBean.getDesc());
                                Log.e(TAG,"onfield--"+likeBean.getCode());
                            }
                        }
                    }
                });
    }
}
