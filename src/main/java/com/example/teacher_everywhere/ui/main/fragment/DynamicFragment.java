package com.example.teacher_everywhere.ui.main.fragment;


import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseFragment;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.BanmiInfo;
import com.example.teacher_everywhere.presenter.EmptyPresenter;
import com.example.teacher_everywhere.ui.main.adapter.RecDynamicAdapter;
import com.example.teacher_everywhere.view.main.EmptyView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends BaseFragment<EmptyView, EmptyPresenter> implements EmptyView {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.recview)
    RecyclerView recview;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    private RecDynamicAdapter adapter;
    private PopupWindow popupWindow;


    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView() {
        recview.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        BanmiInfo.ResultEntity resultEntities = (BanmiInfo.ResultEntity) getArguments().getSerializable(Constants.DATA);
        adapter = new RecDynamicAdapter(resultEntities.getActivities(), getContext());
        recview.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        adapter.setOnImageClickListener(new RecDynamicAdapter.OnImageClickListener() {
            @Override
            public void onClick(String imgUrl) {
                popup(imgUrl);
            }
        });
    }

    private void popup(String imgUrl) {
        View view = View.inflate(getContext(), R.layout.layout_share, null);
        PhotoView photo = view.findViewById(R.id.photo);
        Glide.with(this).load(imgUrl).into(photo);
        photo.enable();
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rlParent,Gravity.CENTER,0,0);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void toastShort(String msg) {

    }


}
