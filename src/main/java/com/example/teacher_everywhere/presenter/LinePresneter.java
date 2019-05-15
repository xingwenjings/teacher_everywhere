package com.example.teacher_everywhere.presenter;

import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.MainDataBean;
import com.example.teacher_everywhere.model.LineModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.MainDataView;

public class LinePresneter extends BasePresenter <MainDataView>{

    private LineModel lineModel;

    @Override
    protected void initModel() {
        lineModel = new LineModel();
        mModels.add(lineModel);
    }

    public void getLineData(int id, int i, String param) {
        lineModel.getLineData(param,id,i, new ResultCallBack<MainDataBean>() {
            @Override
            public void onSuccess(MainDataBean bean) {
                mMvpView.onSuccess(bean.getResult());
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onFail(msg);
            }
        });
    }
}
