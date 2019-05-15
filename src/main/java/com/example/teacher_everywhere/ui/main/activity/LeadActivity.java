package com.example.teacher_everywhere.ui.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.teacher_everywhere.PreviewIndicator;
import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.ui.main.adapter.LeadActivityAdapter;
import com.example.teacher_everywhere.util.SpUtil;

import java.util.ArrayList;

public class LeadActivity extends AppCompatActivity {


    private ViewPager lead_vp;
    private ArrayList<View> views;
    private PreviewIndicator lead_pi;
    private Button two;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        initView();
    }

    private void initView() {

        lead_vp = (ViewPager) findViewById(R.id.lead_vp);
        lead_pi = (PreviewIndicator) findViewById(R.id.lead_pi);
        views = new ArrayList<>();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_lead_vp, null);
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_lead_vp, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_lead_vp_two, null);
        two = view2.findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当点击了按钮后，保存一个状态，将这个状态传到闪屏页中
                    SpUtil.setParam("isPagerOpened",true);
                    Intent intent = new Intent(LeadActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        views.add(view);
        views.add(view1);
        views.add(view2);
        LeadActivityAdapter leadActivityAdapter = new LeadActivityAdapter(views, LeadActivity.this);
        lead_vp.setAdapter(leadActivityAdapter);
        lead_pi.initSize(80,32,6);
        lead_pi.setNumbers(3);
        lead_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    lead_pi.setSelected(0);
                    lead_pi.setVisibility(View.VISIBLE);
                }else if (i==1){
                    lead_pi.setSelected(1);
                    lead_pi.setVisibility(View.VISIBLE);
                }else {
                    lead_pi.setSelected(2);
                    lead_pi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

}
