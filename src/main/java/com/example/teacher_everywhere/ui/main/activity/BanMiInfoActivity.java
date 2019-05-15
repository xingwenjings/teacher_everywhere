package com.example.teacher_everywhere.ui.main.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.BanmiInfo;
import com.example.teacher_everywhere.presenter.BanmiInfoPresenter;
import com.example.teacher_everywhere.ui.main.fragment.DynamicFragment;
import com.example.teacher_everywhere.ui.main.fragment.LineFragment;
import com.example.teacher_everywhere.util.GlideUtil;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.view.main.BanmiInfoView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BanMiInfoActivity extends BaseActivity<BanmiInfoView, BanmiInfoPresenter> implements BanmiInfoView {
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.iv_follow)
    ImageView ivFollow;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_occupation)
    TextView tvOccupation;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    private FragmentManager manager;
    private ArrayList<Fragment> fragments;
    private BanmiInfo.ResultEntity.BanmiEntity banmi;

    @Override
    public void onBanmiXiangSuccess(BanmiInfo banmiInfo) {
        banmi = banmiInfo.getResult().getBanmi();
        GlideUtil.loadUrlRoundImg(10,R.mipmap.zhanweitu_touxiang,R.mipmap.zhanweitu_touxiang, banmi.getPhoto(),img,this);
        if (banmi.isIsFollowed()){
            GlideUtil.loadResImage(R.mipmap.zhanweitu_touxiang,R.mipmap.zhanweitu_touxiang,R.mipmap.follow,ivFollow,this);
        }
        tvName.setText(banmi.getName());
        tvLikeCount.setText(banmi.getFollowing()+"人关注");
        tvCity.setText(banmi.getLocation());
        tvOccupation.setText(banmi.getOccupation());
        tvIntro.setText(banmi.getIntroduction());

        initFragment(banmiInfo);

        manager.beginTransaction().add(R.id.fragment_container,fragments.get(0)).commit();

        setClick();
    }

    @Override
    public void onBanmiXiangField(String msg) {

    }

    private void setClick() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchFragment(0);
                        break;
                    case 1:
                        switchFragment(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragment(BanmiInfo banmiInfo) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.DATA,banmiInfo.getResult());
        fragment.setArguments(bundle);
        fragments.add(fragment);

        LineFragment lineFragment = new LineFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id",banmiInfo.getResult().getBanmi().getId());
        lineFragment.setArguments(bundle1);
        fragments.add(lineFragment);
    }


    @Override
    protected BanmiInfoPresenter initPresenter() {
        return new BanmiInfoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banmi_info;
    }

    @OnClick({R.id.iv_share, R.id.iv_follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                break;
            case R.id.iv_follow:
                break;
        }
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        fragments = new ArrayList<>();
        toolBar.setNavigationIcon(R.mipmap.back_white);
        toolBar.setTitle("");
        tab.addTab(tab.newTab().setText("动态"));
        tab.addTab(tab.newTab().setText("线路"));
        manager = getSupportFragmentManager();
    }

    @Override
    protected void initData() {
        mPresenter.getBanMiXiangData((String) SpUtil.getParam(Constants.TOKEN,""),
                getIntent().getIntExtra(Constants.DATA,0),1);
    }

    @Override
    protected void initListener() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private int lastPosition = 0;
    private void switchFragment(int type) {
        Fragment fragment = fragments.get(type);
        FragmentTransaction tran = manager.beginTransaction();
        if (!fragment.isAdded()){
            tran.add(R.id.fragment_container,fragment);
        }
        tran.hide(fragments.get(lastPosition));
        tran.show(fragment);
        tran.commit();
        lastPosition = type;
    }

    @Override
    public void toastShort(String msg) {

    }
}
