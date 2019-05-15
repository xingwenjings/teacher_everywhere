package com.example.teacher_everywhere.ui.main.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseFragment;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.presenter.BanMiPresenter;
import com.example.teacher_everywhere.ui.main.activity.BanMiInfoActivity;
import com.example.teacher_everywhere.ui.main.adapter.BanmiAdapter;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.util.ToastUtil;
import com.example.teacher_everywhere.view.main.BanMiView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanMiFragment extends BaseFragment<BanMiView, BanMiPresenter> implements BanMiView {

    @BindView(R.id.recView)
    RecyclerView recView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    Unbinder unbinder;
    private ArrayList<BanmiBean.ResultEntity.BanmiEntity> banmiBeans;
    private BanmiAdapter banmiAdapter;
    private int page;

    @Override
    protected BanMiPresenter initPresenter() {
        return new BanMiPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ban_mi;
    }

    @Override
    protected void initView() {
        showLoading();
        banmiBeans = new ArrayList<>();
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        banmiAdapter = new BanmiAdapter(getContext());
        recView.setAdapter(banmiAdapter);
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
        banmiAdapter.setOnFollowCliclListener(new BanmiAdapter.OnFollowCliclListener() {
            @Override
            public void onFollow(int id) {
                mPresenter.addLike((String) SpUtil.getParam(Constants.TOKEN, ""), id);
            }

            @Override
            public void cancelFollow(int id) {
                mPresenter.disLike((String) SpUtil.getParam(Constants.TOKEN, ""), id);
            }
        });
        banmiAdapter.setOnItemClickListener(new BanmiAdapter.OnItemClickListener() {
            @Override
            public void onClick(BanmiBean.ResultEntity.BanmiEntity entity) {
                Intent intent = new Intent(getContext(), BanMiInfoActivity.class);
                intent.putExtra(Constants.DATA,entity.getId());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void initData() {
        mPresenter.getBanmiData(page, (String) SpUtil.getParam(Constants.TOKEN, ""));
    }


    @Override
    public void onSuccess(BanmiBean.ResultEntity entity) {
        hideLoading();
        if (page == 1) {
            banmiBeans.clear();
        }
        banmiBeans.addAll(entity.getBanmi());
        banmiAdapter.setList(banmiBeans);
        srl.finishRefresh();
        srl.finishLoadMore();
    }

    @Override
    public void onFail(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void toastShort(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
