package com.example.teacher_everywhere.presenter;


import com.example.teacher_everywhere.base.BasePresenter;
import com.example.teacher_everywhere.bean.MainDataBean;
import com.example.teacher_everywhere.model.MainModel;
import com.example.teacher_everywhere.net.ResultCallBack;
import com.example.teacher_everywhere.view.main.MainDataView;

/**
 * @author xts
 *         Created by asus on 2019/5/4.
 */

public class MainPresenter extends BasePresenter<MainDataView> {

    private MainModel model;

    @Override
    protected void initModel() {
        model = new MainModel();
        mModels.add(model);
    }

    public void getMainData(int page,String token){
        model.getMainData(page, token, new ResultCallBack<MainDataBean>() {
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
