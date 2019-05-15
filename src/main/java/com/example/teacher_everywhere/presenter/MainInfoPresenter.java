package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.model.MainInfoModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.MainInfoView;

public class MainInfoPresenter extends BasePresenter<MainInfoView> {
    private MainInfoModel model;

    @Override
    protected void initModel() {
        model = new MainInfoModel();
    }

    public void getDataInfo(String token, int id) {
        model.getDataInfo(token, id, new ResultCallBack<MainDataInfo>() {
            @Override
            public void onSuccess(MainDataInfo bean) {
                if (mMvpView != null){
                    mMvpView.updateUi(bean.getResult());
                }
            }

            @Override
            public void onFail(String msg) {
                if (mMvpView != null){
                    mMvpView.onFail(msg);
                }
            }
        });
    }

    public void addLike(String token, int id) {
        model.addLike(token, id, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String bean) {
                mMvpView.onSuccess(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onFail(msg);
            }
        });
    }

    public void disLike(String token, int id) {
        model.disLike(token, id, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String bean) {
                mMvpView.onSuccess(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onFail(msg);
            }
        });
    }
}
