package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.BanmiInfo;
import com.example.teacher_everywhere.model.BanmiInfoModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.BanmiInfoView;

public class BanmiInfoPresenter extends BasePresenter<BanmiInfoView> {

    private BanmiInfoModel banmiXiangModel;

    @Override
    protected void initModel() {
        banmiXiangModel = new BanmiInfoModel();
    }

    public void getBanMiXiangData(String param,int id ,int intExtra) {
        banmiXiangModel.getBanmiInfo(param, id, intExtra, new ResultCallBack<BanmiInfo>() {
            @Override
            public void onSuccess(BanmiInfo bean) {
                if (mMvpView != null){
                    mMvpView.onBanmiXiangSuccess(bean);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mMvpView != null){
                    mMvpView.onBanmiXiangField(msg);
                }
            }
        });
    }
}
