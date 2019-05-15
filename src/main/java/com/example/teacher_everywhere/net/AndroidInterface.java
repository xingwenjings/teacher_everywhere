package com.example.teacher_everywhere.net;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.ui.main.activity.SubjectActivity;
import com.example.teacher_everywhere.ui.main.activity.informent_activity.MainDataInfoActivity;
import com.just.agentweb.AgentWeb;

public class AndroidInterface {
    private AgentWeb agentWeb;
    Context context;

    public AndroidInterface(AgentWeb agentWeb, Context context) {
        this.agentWeb = agentWeb;
        this.context = context;
    }
    @JavascriptInterface
    public void callAndroid(String type,int id){
        Intent intent = new Intent(context, MainDataInfoActivity.class);
        intent.putExtra(Constants.DATA,id);
        context.startActivity(intent);
    }
    @JavascriptInterface
    public void callAndroid(String type) {
//        callAndroid('route_details', id)
        context.startActivity(new Intent(context, SubjectActivity.class));
    }
}
