package com.example.teacher_everywhere.ui.main.fragment;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseFragment;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.MainDataBean;
import com.example.teacher_everywhere.presenter.MainPresenter;
import com.example.teacher_everywhere.ui.main.activity.WebViewActivity;
import com.example.teacher_everywhere.ui.main.activity.informent_activity.MainDataInfoActivity;
import com.example.teacher_everywhere.ui.main.adapter.MainDataAdapter;
import com.example.teacher_everywhere.util.Logger;
import com.example.teacher_everywhere.view.main.MainDataView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainDataFragment extends BaseFragment<MainDataView, MainPresenter> implements MainDataView {
    @BindView(R.id.recView)
    RecyclerView recView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private ArrayList<MainDataBean.ResultEntity.BannersEntity> banners;
    private ArrayList<MainDataBean.ResultEntity.RoutesEntity> routes;
    private MainDataAdapter adapter;
    private int page = 1;

    @Override
    public void onSuccess(MainDataBean.ResultEntity resultEntity) {
        hideLoading();
        if (page == 1){
            banners.clear();
            routes.clear();
        }
        banners.addAll(resultEntity.getBanners());
        routes.addAll(resultEntity.getRoutes());
        adapter.setBanners(banners);
        adapter.setRoutes(routes);
        srl.finishLoadMore();
        srl.finishRefresh();
    }

    private static final String TAG = "MainFragment";
    @Override
    public void onFail(String msg) {
        Logger.logD(TAG,msg);
    }


    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        banners = new ArrayList<>();
        routes = new ArrayList<>();
        adapter = new MainDataAdapter(getContext());
        recView.setAdapter(adapter);
        showLoading();

        adapter.setOnItemClickListener(new MainDataAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                MainDataBean.ResultEntity.RoutesEntity entity = routes.get(position);
                Intent intent = new Intent(getContext(), MainDataInfoActivity.class);
                intent.putExtra(Constants.DATA,entity.getId());
                startActivity(intent);
            }
        });

        adapter.setOnBundleClickListener(new MainDataAdapter.onBundleClickListener() {
            @Override
            public void onClick(String url, String title) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra(Constants.DATA,url+"?os=android");
                intent.putExtra(Constants.TITLE,title);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getMainData(page,getArguments().getString(Constants.DATA));

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
                page = 1;
                initData();
            }
        });
    }

    @Override
    public void toastShort(String msg) {

    }
}
