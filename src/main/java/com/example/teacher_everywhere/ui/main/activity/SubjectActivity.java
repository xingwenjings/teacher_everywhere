package com.example.teacher_everywhere.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.BundleBean;
import com.example.teacher_everywhere.presenter.SubjectPresenter;
import com.example.teacher_everywhere.ui.main.adapter.RecSubjectAdapter;
import com.example.teacher_everywhere.view.main.SubjectView;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectActivity extends BaseActivity<SubjectView, SubjectPresenter> implements SubjectView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recView)
    RecyclerView recView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private ArrayList<BundleBean.ResultBean.BundlesBean> list;
    private RecSubjectAdapter adapter;

    @Override
    protected SubjectPresenter initPresenter() {
        return new SubjectPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle("");
        list = new ArrayList<>();
        toolbar.setNavigationIcon(R.mipmap.back_white);
        adapter = new RecSubjectAdapter(this);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        mPresenter.getBundles();
    }

    @Override
    protected void initListener() {
        adapter.setOnBundleClickListener(new RecSubjectAdapter.OnBundleClickListener() {
            @Override
            public void onClick(String url, String title) {
                Intent intent = new Intent(SubjectActivity.this, WebViewActivity.class);
                intent.putExtra(Constants.DATA,url+"?os=android");
                intent.putExtra(Constants.TITLE,title);
                startActivity(intent);
                finish();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    public void onBundleSuccess(BundleBean.ResultBean resultBean) {
        list = (ArrayList<BundleBean.ResultBean.BundlesBean>) resultBean.getBundles();
        adapter.setList(list);
        srl.finishRefresh();
        srl.finishLoadMore();
    }

    @Override
    public void onBundleField(String msg) {

    }

}
