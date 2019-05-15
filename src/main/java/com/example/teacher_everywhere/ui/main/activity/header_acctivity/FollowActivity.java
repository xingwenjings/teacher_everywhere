package com.example.teacher_everywhere.ui.main.activity.header_acctivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.presenter.my.FollowPresenter;
import com.example.teacher_everywhere.ui.main.adapter.BanmiAdapter;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.view.my.FollowView;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
//侧滑中我的关注
public class FollowActivity extends BaseActivity<FollowView, FollowPresenter> implements FollowView {


    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recView)
    RecyclerView recView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    private ArrayList<BanmiBean.ResultEntity.BanmiEntity> banmiBeans;
    private BanmiAdapter recAboutAdapter;
    private int page;

    @Override
    protected FollowPresenter initPresenter() {
        return new FollowPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        banmiBeans = new ArrayList<>();
        recView.setLayoutManager(new LinearLayoutManager(this));
        recAboutAdapter = new BanmiAdapter(this);
        recView.setAdapter(recAboutAdapter);
        toolBar.setNavigationIcon(R.mipmap.back_white);
    }

    @Override
    public void toastShort(String msg) {

    }


    @Override
    public void setSuccess(BanmiBean.ResultEntity resultEntity) {
        if (page == 1){
            banmiBeans.clear();
        }
        banmiBeans.addAll(resultEntity.getBanmi());
        recAboutAdapter.setList(banmiBeans);
        srl.finishLoadMore();
        srl.finishRefresh();
    }

    @Override
    public void setField(String msg) {

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
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getLikeData((String) SpUtil.getParam(Constants.TOKEN,""),page);
    }

}
