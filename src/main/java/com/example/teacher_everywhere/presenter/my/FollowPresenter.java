package com.example.teacher_everywhere.presenter.my;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.model.my.FollowModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.my.FollowView;


public class FollowPresenter extends BasePresenter<FollowView> {

    private FollowModel aboutModel;

    @Override
    protected void initModel() {
        aboutModel = new FollowModel();
        mModels.add(aboutModel);
    }
    public void getLikeData(String token,int page){
        aboutModel.getLikeData(page, token, new ResultCallBack<BanmiBean>() {
            @Override
            public void onSuccess(BanmiBean bean) {
                mMvpView.setSuccess(bean.getResult());
            }

            @Override
            public void onFail(String msg) {
                mMvpView.setField(msg);
            }
        });
    }
}
