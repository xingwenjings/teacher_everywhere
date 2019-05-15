package com.example.teacher_everywhere.ui.main.activity;
//5cceb6254ca3571a71000a17   apk
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.presenter.LoginPresenter;
import com.example.teacher_everywhere.ui.main.fragment.LoginOrBindFragment;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.util.Tools;
import com.example.teacher_everywhere.view.main.LoginView;
import com.umeng.socialize.UMShareAPI;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {
    public static final int TYPE_LOGIN = 0;
    public static final int TYPE_BIND = 1;
    private int mType;
    public static String TAG="LoginOrBindFragment";

    public static void startAct(Context context,int type){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.TYPE,type);
        context.startActivity(intent);
    }
    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        mPresenter.getVerifyCode();
    }

    @Override
    protected void initView() {
        outLogin();
        Tools.addShortcut(this,R.mipmap.w08,this);
        getIntentData();
        FragmentManager manager = getSupportFragmentManager();
        LoginOrBindFragment fragment = LoginOrBindFragment.get_visiable(mType);
        manager.beginTransaction().add(R.id.fl_container,fragment,TAG).commit();
    }

    private void outLogin() {
        //保存登录状态
        if (!TextUtils.isEmpty((String) SpUtil.getParam(Constants.TOKEN,""))){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    private void getIntentData() {
        mType=getIntent().getIntExtra(Constants.TYPE,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //内存泄漏解决方案
        UMShareAPI.get(this).release();
    }


    @Override
    public void toastShort(String msg) {

    }
}
