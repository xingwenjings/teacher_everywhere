package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.BundleBean;
import com.example.teacher_everywhere.model.SubjectModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.SubjectView;

public class SubjectPresenter extends BasePresenter<SubjectView> {

    private SubjectModel subjectModel;

    @Override
    protected void initModel() {
        subjectModel = new SubjectModel();
        mModels.add(subjectModel);
    }

    public void getBundles() {
        subjectModel.getBundleData(new ResultCallBack<BundleBean>() {
            @Override
            public void onSuccess(BundleBean bean) {
                mMvpView.onBundleSuccess(bean.getResult());
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onBundleField(msg);
            }
        });
    }
}
