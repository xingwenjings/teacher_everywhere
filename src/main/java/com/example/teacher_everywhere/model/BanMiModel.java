package com.example.teacher_everywhere.model;

import android.util.Log;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.Disposable;

public class BanMiModel extends BaseModel {
    public void getBanmiData(int page, String token, final ResultCallBack<BanmiBean> callBack) {
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        service.getBanmiData(page, token)
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

    private static final String TAG = "BanmiModel";
    public void addLike(String token, int id, final ResultCallBack<String> callBack) {
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        service.addFollow(token, id)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void error(String msg) {
                        Log.e(TAG, "error: e=" + msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("code") == EveryWhereService.SUCCESS_CODE) {
                                callBack.onSuccess(jsonObject.getString("desc"));
                            } else {
                                callBack.onFail(jsonObject.getString("desc"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void disLike(String token, int id, final ResultCallBack<String> callBack) {
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        service.disFollow(token, id)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void error(String msg) {
                        Log.e(TAG, "error: e=" + msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("code") == EveryWhereService.SUCCESS_CODE) {
                                callBack.onSuccess(jsonObject.getString("desc"));
                            } else {
                                callBack.onFail(jsonObject.getString("desc"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
