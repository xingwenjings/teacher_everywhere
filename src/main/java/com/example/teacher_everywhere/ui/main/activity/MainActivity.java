package com.example.teacher_everywhere.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.ui.main.activity.header_acctivity.FollowActivity;
import com.example.teacher_everywhere.ui.main.activity.header_acctivity.LikeActivity;
import com.example.teacher_everywhere.ui.main.activity.informent_activity.InformationActivity;
import com.example.teacher_everywhere.ui.main.adapter.MainAdapter;
import com.example.teacher_everywhere.ui.main.fragment.BanMiFragment;
import com.example.teacher_everywhere.ui.main.fragment.MainDataFragment;
import com.example.teacher_everywhere.util.SpUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
//首页
public class MainActivity extends AppCompatActivity {


    private ImageView main_girl;
    private ImageView main_box;
    private Toolbar main_toolbar;
    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<Fragment> fragments;
    private RelativeLayout rl;
    private NavigationView nv;
    private DrawerLayout dl;
    private FragmentManager manager;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        int a=111111;
        String name="dfshjahfak";
        initToolbar();
        initNa();
        initData();
    }

    private void initNa() {
        nv.setItemIconTintList(null);
        View view = nv.getHeaderView(0);
        ImageView img = view.findViewById(R.id.head_img);
        TextView head_about = view.findViewById(R.id.head_about);
        RelativeLayout rl_head = view.findViewById(R.id.rl_ll);

        rl_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
        //点击跳到我的关注
        head_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FollowActivity.class);
                startActivity(intent);
            }
        });

        //点击侧滑我的收藏跳到收藏界面
        TextView header_collect = view.findViewById(R.id.header_collect);
        header_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LikeActivity.class);
                startActivity(intent);
            }
        });
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        //Glide.with(MainActivity.this).load(R.mipmap.w08).apply(requestOptions).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.closeDrawer(Gravity.LEFT);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(main_toolbar);
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        //Glide.with(MainActivity.this).load(R.mipmap.w08).apply(requestOptions).into(main_girl);
        main_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initData() {
        fragments = new ArrayList<>();
        MainDataFragment homeFragment = new MainDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DATA,(String) SpUtil.getParam(Constants.TOKEN, ""));
        homeFragment.setArguments(bundle);
        fragments.add(homeFragment);
        fragments.add(new BanMiFragment());


        tab.addTab(tab.newTab().setText(R.string.home_text).setIcon(R.drawable.select_home));
        tab.addTab(tab.newTab().setText(R.string.banmi_text).setIcon(R.drawable.select_banmi));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(mainAdapter);
    }

    private void initView() {
        main_girl = (ImageView) findViewById(R.id.main_girl);
        main_box = (ImageView) findViewById(R.id.main_box);
        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);
        rl = (RelativeLayout) findViewById(R.id.rl);
        nv = (NavigationView) findViewById(R.id.nv);
        dl = (DrawerLayout) findViewById(R.id.dl);

        StatusBarUtil.setLightMode(this);
    }
}
