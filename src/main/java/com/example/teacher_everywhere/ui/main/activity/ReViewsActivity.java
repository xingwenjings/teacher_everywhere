package com.example.teacher_everywhere.ui.main.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.presenter.ReViewsPresneter;
import com.example.teacher_everywhere.ui.main.adapter.RecReviewsAdapter;
import com.example.teacher_everywhere.view.main.ReViewsView;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
//评价
public class ReViewsActivity extends BaseActivity<ReViewsView, ReViewsPresneter> implements ReViewsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl)
    RecyclerView rl;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    private int page=1;
    private ArrayList<MainDataInfo.ResultEntity.ReviewsEntity> reviewsEntities;
    private RecReviewsAdapter recReviewsAdapter;

    @Override
    protected ReViewsPresneter initPresenter() {
        return new ReViewsPresneter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_re_views;
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle("");
        reviewsEntities = new ArrayList<>();
        rl.setLayoutManager(new LinearLayoutManager(this));
        recReviewsAdapter = new RecReviewsAdapter(this);
        rl.setAdapter(recReviewsAdapter);
        toolbar.setNavigationIcon(R.mipmap.back_white);
    }

    @Override
    protected void initData() {
        mPresenter.getReviewsData(getIntent().getIntExtra(Constants.DATA,0),page);
    }

    @Override
    public void onSucessReView(MainDataInfo.ResultEntity resultEntity) {
        reviewsEntities.addAll(resultEntity.getReviews());
        recReviewsAdapter.setReviewsEntities(reviewsEntities);
        srl.finishLoadMore();
        srl.finishRefresh();

    }

    @Override
    protected void initListener() {
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (page==1){
                    reviewsEntities.clear();
                }
                initData();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onFieldReView(String msg) {

    }

}
