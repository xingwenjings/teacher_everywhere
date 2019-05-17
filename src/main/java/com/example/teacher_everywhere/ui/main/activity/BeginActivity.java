package com.example.teacher_everywhere.ui.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.util.Tools;

public class BeginActivity extends AppCompatActivity {

    private ImageView begin_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        initView();
    }

    private void initView() {
        begin_img = (ImageView) findViewById(R.id.begin_img);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,1);
        alphaAnimation.setDuration(2000);
        begin_img.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences lead = getSharedPreferences("lead", MODE_PRIVATE);//保存状态
                if ((boolean)SpUtil.getParam("isPagerOpened",false)){
                    startActivity(new Intent(BeginActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(BeginActivity.this,LeadActivity.class));
                }
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
