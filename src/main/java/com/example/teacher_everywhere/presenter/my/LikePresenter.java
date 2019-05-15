package com.example.teacher_everywhere.presenter.my;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.LikeBean;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.my.LikeView;

public class LikePresenter extends BasePresenter<LikeView> {
    private com.example.teacher_everywhere.model.my.LikeModel model;

    @Override
    protected void initModel() {
        model = new com.example.teacher_everywhere.model.my.LikeModel();
    }


    public void getLikeData(int page){
        model.getCollectData(page, new ResultCallBack<LikeBean>() {
            @Override
            public void onSuccess(LikeBean bean) {
                if (mMvpView != null){
                    mMvpView.onSuccess(bean.getResult());
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

}
