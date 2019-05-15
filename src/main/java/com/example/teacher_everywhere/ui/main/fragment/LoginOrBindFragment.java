package com.example.teacher_everywhere.ui.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseFragment;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.presenter.LoginOrBindPresenter;
import com.example.teacher_everywhere.ui.main.activity.LoginActivity;
import com.example.teacher_everywhere.ui.main.activity.MainActivity;
import com.example.teacher_everywhere.ui.main.activity.WebActivity;
import com.example.teacher_everywhere.util.ToastUtil;
import com.example.teacher_everywhere.util.Tools;
import com.example.teacher_everywhere.view.main.LoginOrBindView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginOrBindFragment extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.services)
    TextView services;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.phone)
    ImageView phone;
    @BindView(R.id.country_code)
    TextView countryCode;
    @BindView(R.id.selects)
    ImageView selects;
    @BindView(R.id.send_code)
    Button sendCode;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.ll_view)
    View llView;
    @BindView(R.id.ll_or)
    LinearLayout llOr;
    @BindView(R.id.wechat)
    ImageView wechat;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.sina)
    ImageView sina;
    @BindView(R.id.logins)
    LinearLayout logins;
    @BindView(R.id.wachat_login)
    TextView wachatLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.agree)
    TextView agree;
    private int mType;
    public static int COUNT_DOWN_TIME=10;
    private int mTime = COUNT_DOWN_TIME;
    private String mVerifyCode;
    private Handler handler;
    private VerifyFragment mVerifyFragment;


    public static LoginOrBindFragment get_visiable(int type){
        LoginOrBindFragment fragment = new LoginOrBindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected LoginOrBindPresenter initPresenter() {
        return new LoginOrBindPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_or_bind;
    }

    @OnClick({R.id.back,R.id.send_code, R.id.wechat, R.id.qq, R.id.sina})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.send_code:
                //如果点击发送验证码，而手机号为空的话，给出提示语句
                if (TextUtils.isEmpty(etPhone.getText())){
                    ToastUtil.showShort("手机号不能为空");
                }
                //获取验证码
                getVerifyCode();
                addVerifyFragment();
                time();
                break;
            case R.id.wechat:
                mPresenter.oauthLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.qq:
                mPresenter.oauthLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.sina:
                mPresenter.oauthLogin(SHARE_MEDIA.SINA);
                break;
        }
    }

    private void time() {
        //避免多次执行倒计时
        if (mTime>0&&mTime<COUNT_DOWN_TIME){
            return;
        }
        countDown();
    }
    //handler倒计时    内存泄漏
    public void countDown() {
        if (handler==null){
            handler = new Handler();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mTime<=0){
                    mTime=COUNT_DOWN_TIME;
                    return;
                }
                mTime--;
                if (mVerifyFragment!=null){
                    mVerifyFragment.setCountDownTime(mTime);
                }
                countDown();
            }
        },1000);
    }

    private void getVerifyCode() {
        //获取验证码，如果倒计时还在运行，则说明不用发送验证码
        if (mTime>0&&mTime<COUNT_DOWN_TIME-1){
            return;
        }
        mVerifyCode="";
        mPresenter.getVerifyCode();
    }

    private void addVerifyFragment() {
        if (TextUtils.isEmpty(getPhone())) {
            return;
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //添加到回退栈
        fragmentTransaction.addToBackStack(null);
        mVerifyFragment = VerifyFragment.newIntance(mVerifyCode);
        fragmentTransaction.add(R.id.fl_container, mVerifyFragment).commit();
        //关闭软键盘
        Tools.closeKeyBoard(getActivity());
    }

    @Override
    protected void initListener() {
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switchBtnState(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void switchBtnState(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            sendCode.setBackgroundResource(R.drawable.bg_btn_ea_r15);
        } else {
            sendCode.setBackgroundResource(R.drawable.bg_btn_ea_aa_r15);
        }
    }


    @Override
    public String getPhone() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public Activity getAct() {
        return getActivity();
    }

    @Override
    public void go2MainActivity() {
        MainActivity.startAct(getContext());
    }

    @Override
    public void setCode(String code) {
        this.mVerifyCode=code;
        if (mVerifyFragment!=null){
            mVerifyFragment.setData(code);
        }
    }

    @Override
    protected void initView() {
        getArgumentsData();
        spannable();
        shouOrHideView();
    }

    private void shouOrHideView() {
        if (mType==LoginActivity.TYPE_LOGIN){
            back.setVisibility(View.INVISIBLE);
            logins.setVisibility(View.VISIBLE);
            llOr.setVisibility(View.VISIBLE);
        }else {
            //绑定
            back.setVisibility(View.VISIBLE);
            logins.setVisibility(View.GONE);
            llOr.setVisibility(View.GONE);
        }
    }

    private void getArgumentsData() {
        Bundle arguments = getArguments();
        mType = arguments.getInt(Constants.TYPE);
    }

    private void spannable() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getResources().getString(R.string.agree_protocol));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                WebActivity.startAct(getContext());
            }
        };
        spannableStringBuilder.setSpan(clickableSpan,11,15,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableStringBuilder.setSpan(underlineSpan,11,15,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.c_FA6A13));
        spannableStringBuilder.setSpan(span,11,15,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        //clickableSpan需要设置这个才有效果
        agree.setMovementMethod(LinkMovementMethod.getInstance());
        agree.setText(spannableStringBuilder);
    }


    @Override
    public void toastShort(String msg) {

    }
}
