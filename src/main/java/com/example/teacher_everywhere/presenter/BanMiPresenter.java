package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.model.BanMiModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.BanMiView;

public class BanMiPresenter extends BasePresenter<BanMiView> {
    private BanMiModel model;

    @Override
    protected void initModel() {
        model = new BanMiModel();
        mModels.add(model);
    }

    public void getBanmiData(int page,String token){
        model.getBanmiData(page, token, new ResultCallBack<BanmiBean>() {
            @Override
            public void onSuccess(BanmiBean bean) {
                mMvpView.onSuccess(bean.getResult());
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onFail(msg);
            }
        });
    }

    public void addLike(String token,int id){
        model.addLike(token, id, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String bean) {
                mMvpView.toastShort(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.toastShort(msg);
            }
        });
    }

    public void disLike(String token,int id){
        model.disLike(token, id, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String bean) {
                mMvpView.toastShort(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.toastShort(msg);
            }
        });
    }
}
