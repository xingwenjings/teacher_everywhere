package com.example.teacher_everywhere.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseApp;
import com.example.teacher_everywhere.base.BaseFragment;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.presenter.VerifyPresenter;
import com.example.teacher_everywhere.ui.main.activity.LoginActivity;
import com.example.teacher_everywhere.ui.main.activity.MainActivity;
import com.example.teacher_everywhere.util.Logger;
import com.example.teacher_everywhere.view.main.VerifyView;
import com.example.teacher_everywhere.widget.IdentifyingCodeView;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifyFragment extends BaseFragment<VerifyView, VerifyPresenter> implements VerifyView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_send_again)
    TextView tvSendAgain;
    @BindView(R.id.icv)
    IdentifyingCodeView icv;
    @BindView(R.id.tv_wait)
    TextView tvWait;
    private int mTime;


    public static VerifyFragment newIntance(String code){
        VerifyFragment verifyFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VERIFY_CODE,code);
        verifyFragment.setArguments(bundle);
        return verifyFragment;
    }
    @Override
    protected VerifyPresenter initPresenter() {
        return new VerifyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.verify_fragment;
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.iv_back,R.id.tv_send_again})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                pop();
                break;
            case R.id.tv_send_again:
                if (mTime==0){
                    mPresenter.getVerifyCode();
                    LoginOrBindFragment fragment = (LoginOrBindFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LoginActivity.TAG);
                    fragment.countDown();
                }
                break;
        }
    }



    private void pop() {
        //碎片手动弹栈
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }

    @Override
    public void toastShort(String msg) {

    }


    @Override
    public void setData(String data) {
        if (!TextUtils.isEmpty(data)&&tvWait!=null){
            tvWait.setText(BaseApp.getRes().getString(R.string.get_verify_code)+data);
        }
    }

    @Override
    protected void initListener() {
        icv.setOnEditorActionListener(new IdentifyingCodeView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }

            @Override
            public void onTextChanged(String s) {
                autoLogin();
            }
        });
    }

    private void autoLogin() {
        Logger.println(icv.getTextContent());
        if (icv.getTextContent().length()>=4){
            toastShort("自动登录");
            icv.setBackgroundEnter(false);
            tvWait.setText(BaseApp.getRes().getString(R.string.wait_please));
            showLoading();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

    @Override
    protected void initView() {
        String code = getArguments().getString(Constants.VERIFY_CODE);
        setData(code);

    }

    public void  setCountDownTime(int time){
        mTime=time;
        if (tvSendAgain!=null){
           if (time!=0){
               String format = String.format(getResources().getString(R.string.send_again) + "(%ss)", time);
               tvSendAgain.setText(format);
               tvSendAgain.setTextColor(getResources().getColor(R.color.c_999999));
           }else {
               tvSendAgain.setText(getResources().getString(R.string.send_again));
               tvSendAgain.setTextColor(getResources().getColor(R.color.c_fa6a13));
           }
        }
    }

}
