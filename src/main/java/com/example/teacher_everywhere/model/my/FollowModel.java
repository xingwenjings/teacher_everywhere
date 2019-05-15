package com.example.teacher_everywhere.model.my;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import io.reactivex.disposables.Disposable;

public class FollowModel extends BaseModel {
    public void getLikeData(int page, String token, final ResultCallBack<BanmiBean> callBack) {
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        service.getLikeData(token, page)
                .compose(RxUtils.<BanmiBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BanmiBean>() {
                    @Override
                    public void error(String msg) {
                        callBack.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BanmiBean banmiBean) {
                        if (banmiBean != null) {
                            if (banmiBean.getCode() == EveryWhereService.SUCCESS_CODE) {
                                callBack.onSuccess(banmiBean);
                            } else {
                                callBack.onFail(banmiBean.getDesc());
                            }
                        }
                    }
                });
    }
}
