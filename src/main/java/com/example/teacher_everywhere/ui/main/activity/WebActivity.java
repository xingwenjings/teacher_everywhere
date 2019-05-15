package com.example.teacher_everywhere.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.presenter.EmptyPresenter;
import com.example.teacher_everywhere.view.main.EmptyView;
import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    private AgentWeb mAgentWeb;


    public static void startAct(Context context){
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {

        //亮色的模式,会讲状态栏文字修改为黑色的
        StatusBarUtil.setLightMode(this);

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back_white);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAgentWeb.back()){
                    finish();
                }
            }
        });

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llContainer, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go("https://api.banmi.com/app2017/agreement.html");

        mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!TextUtils.isEmpty(title)){
                    toolbarTitle.setText(title);
                }
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void toastShort(String msg) {

    }
}
