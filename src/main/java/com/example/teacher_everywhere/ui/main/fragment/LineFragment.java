package com.example.teacher_everywhere.ui.main.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseFragment;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.MainDataBean;
import com.example.teacher_everywhere.presenter.LinePresneter;
import com.example.teacher_everywhere.ui.main.activity.informent_activity.MainDataInfoActivity;
import com.example.teacher_everywhere.ui.main.adapter.MainDataAdapter;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.view.main.MainDataView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LineFragment extends BaseFragment<MainDataView, LinePresneter> implements MainDataView {

    @BindView(R.id.recView)
    RecyclerView recView;
    private MainDataAdapter adapter;
    @Override
    protected LinePresneter initPresenter() {
        return new LinePresneter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_line;
    }

    @Override
    public void onSuccess(final MainDataBean.ResultEntity resultEntity) {
        adapter.setRoutes(resultEntity.getRoutes());
        adapter.setOnItemClickListener(new MainDataAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), MainDataInfoActivity.class);
                intent.putExtra(Constants.DATA,resultEntity.getRoutes().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    protected void initView() {
        recView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter=new MainDataAdapter(getContext());
        recView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.getLineData(getArguments().getInt("id"),1,(String)SpUtil.getParam(Constants.TOKEN,""));
    }

    @Override
    public void toastShort(String msg) {

    }




}
