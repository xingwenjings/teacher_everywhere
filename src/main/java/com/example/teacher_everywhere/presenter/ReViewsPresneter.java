package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.model.ReViewsModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.ReViewsView;

public class ReViewsPresneter extends BasePresenter<ReViewsView> {

    private ReViewsModel reViewsModel;

    @Override
    protected void initModel() {
        reViewsModel = new ReViewsModel();
        mModels.add(reViewsModel);
    }

    public void getReviewsData(int id, int page) {
        reViewsModel.getReviews(id, page, new ResultCallBack<MainDataInfo>() {
            @Override
            public void onSuccess(MainDataInfo bean) {
                mMvpView.onSucessReView(bean.getResult());
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onFieldReView(msg);
            }
        });
    }
}
