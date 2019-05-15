package com.example.teacher_everywhere.model;

import android.util.Log;

import com.example.teacher_everywhere.base.BaseModel;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.net.BaseObserver;
import com.example.teacher_everywhere.net.EveryWhereService;
import com.example.teacher_everywhere.net.HttpUtils;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.net.RxUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.disposables.Disposable;

public class MainInfoModel extends BaseModel {

    private static final String TAG = "MainInfoModel";

    public void getDataInfo(String token, int id, final ResultCallBack<MainDataInfo> callBack) {
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        service.getMainDataInfo(token, id)
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
                        if (mainDataInfo != null) {
                            if (mainDataInfo.getCode() == EveryWhereService.SUCCESS_CODE) {
                                callBack.onSuccess(mainDataInfo);
                            } else {
                                callBack.onFail(mainDataInfo.getDesc());
                            }
                        }
                    }
                });
    }

    public void addLike(String token, int id, final ResultCallBack<String> callBack) {
        EveryWhereService service = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        service.addLike(token, id)
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
        service.disLike(token, id)
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
