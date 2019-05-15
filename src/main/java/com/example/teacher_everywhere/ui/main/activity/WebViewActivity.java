package com.example.teacher_everywhere.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.net.AndroidInterface;
import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;

public class WebViewActivity extends AppCompatActivity {

    private TextView tb_title;
    private Toolbar toolbar;
    private LinearLayout ll;
    private AgentWeb agentWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("");
        tb_title.setText(getIntent().getStringExtra(Constants.TITLE));
        toolbar.setNavigationIcon(R.mipmap.back_white);
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) ll, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .createAgentWeb()
                .ready()
                .go(getIntent().getStringExtra(Constants.DATA));

        agentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface(agentWeb,this));
        initListener();

    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        tb_title = (TextView) findViewById(R.id.tb_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ll = (LinearLayout) findViewById(R.id.ll);
    }
}
