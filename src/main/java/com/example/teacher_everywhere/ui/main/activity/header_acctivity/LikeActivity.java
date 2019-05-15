package com.example.teacher_everywhere.ui.main.activity.header_acctivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.LikeBean;
import com.example.teacher_everywhere.presenter.my.LikePresenter;
import com.example.teacher_everywhere.ui.main.activity.informent_activity.MainDataInfoActivity;
import com.example.teacher_everywhere.ui.main.adapter.RecLikeAdapter;
import com.example.teacher_everywhere.util.ToastUtil;
import com.example.teacher_everywhere.view.my.LikeView;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
//侧滑中我的收藏
public class LikeActivity extends BaseActivity<LikeView, LikePresenter> implements LikeView {


    @BindView(R.id.recView)
    RecyclerView recView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    private RecLikeAdapter adapter;
    private ArrayList<LikeBean.ResultEntity.CollectedRoutesEntity> list;
    private int page = 1;

    @Override
    protected LikePresenter initPresenter() {
        return new LikePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void onSuccess(LikeBean.ResultEntity resultEntity) {
        if (page == 1){
            list.clear();
        }
        list.addAll(resultEntity.getCollectedRoutes());
        adapter.setList(list);
        srl.finishRefresh();
        srl.finishLoadMore();
    }

    @Override
    public void onFail(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        list = new ArrayList<>();
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecLikeAdapter(this);
        recView.setAdapter(adapter);
        toolbar.setNavigationIcon(R.mipmap.back_white);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new RecLikeAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(LikeActivity.this, MainDataInfoActivity.class);
                intent.putExtra(Constants.DATA,list.get(position).getId());
                startActivity(intent);
            }
        });
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getLikeData(page);
    }

    @Override
    public void toastShort(String msg) {

    }
}
