package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.VersionBean;
import com.example.teacher_everywhere.model.MainVeisionModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.MainVersionView;

public class MainVersionPresenter extends BasePresenter<MainVersionView> {

    private MainVeisionModel mainVeisionModel;

    @Override
    protected void initModel() {
        mainVeisionModel = new MainVeisionModel();
        mModels.add(mainVeisionModel);
    }

    public void getVersionData(String param) {
        mainVeisionModel.getVersionData(param, new ResultCallBack<VersionBean>() {
            @Override
            public void onSuccess(VersionBean bean) {
                mMvpView.setOnsuccess(bean.getResult());
            }

            @Override
            public void onFail(String msg) {
                mMvpView.setField(msg);
            }
        });
    }
}
