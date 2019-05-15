package com.example.teacher_everywhere.ui.main.activity.informent_activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.base.BaseActivity;
import com.example.teacher_everywhere.base.Constants;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.presenter.MainInfoPresenter;
import com.example.teacher_everywhere.ui.main.activity.ReViewsActivity;
import com.example.teacher_everywhere.ui.main.adapter.MainDataInfoAdapter;
import com.example.teacher_everywhere.util.SpUtil;
import com.example.teacher_everywhere.view.main.MainInfoView;
import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import static com.bumptech.glide.Glide.*;

import butterknife.BindView;
import butterknife.OnClick;
//首页详情
public class MainDataInfoActivity extends BaseActivity<MainInfoView, MainInfoPresenter> implements MainInfoView {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.recView)
    RecyclerView recView;
    @BindView(R.id.bt_startLine)
    Button btStartLine;
    @BindView(R.id.btn_show_line)
    Button btShowLine;
    @BindView(R.id.btn_price)
    Button btPrice;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    private MainDataInfoAdapter adapter;
    private PopupWindow popupWindow;

    @Override
    public void updateUi(MainDataInfo.ResultEntity entity) {
        if (entity.getRoute().isIsPurchased()) {
            btShowLine.setVisibility(View.GONE);
            btPrice.setVisibility(View.GONE);
            btStartLine.setVisibility(View.VISIBLE);
        } else {
            btShowLine.setVisibility(View.VISIBLE);
            btPrice.setVisibility(View.VISIBLE);
            btStartLine.setVisibility(View.GONE);
        }
        adapter = new MainDataInfoAdapter(this, entity);
        recView.setAdapter(adapter);
        setClick(entity.getRoute());
    }

    private void setClick(final MainDataInfo.ResultEntity.RouteEntity route) {
        adapter.setOnItemLikeListener(new MainDataInfoAdapter.OnItemLikeListener() {
            @Override
            public void addLike(int id) {
                mPresenter.addLike((String) SpUtil.getParam(Constants.TOKEN, ""), id);
            }

            @Override
            public void disLike(int id) {
                mPresenter.disLike((String) SpUtil.getParam(Constants.TOKEN, ""), id);
            }
        });

        adapter.setOnImageClickListener(new MainDataInfoAdapter.OnImageClickListener() {
            @Override
            public void onClick(String imgUrl) {
                popup(imgUrl);
            }
        });

        adapter.setOnReViewsClickListener(new MainDataInfoAdapter.OnReViewsClickListener() {
            @Override
            public void onReViewClick(int id) {
                Intent intent = new Intent(MainDataInfoActivity.this, ReViewsActivity.class);
                intent.putExtra(Constants.DATA,id);
                startActivity(intent);
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMImage image = new UMImage(MainDataInfoActivity.this, route.getCardURL());
                UMWeb web = new UMWeb(route.getShareURL());
                web.setTitle(route.getTitle());//标题
                web.setThumb(image);  //缩略图
                web.setDescription(route.getIntro());//描述
                new ShareAction(MainDataInfoActivity.this).withText(route.getIntro()).withMedia(web).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA)
                        .setCallback(umShareListener).open();
            }
        });
    }

    private void popup(String imgUrl) {
        View view = View.inflate(this, R.layout.layout_share, null);
        PhotoView photo = view.findViewById(R.id.photo);
        with(this).load(imgUrl).into(photo);
        photo.enable();
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rlParent, Gravity.CENTER,0,0);
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    protected MainInfoPresenter initPresenter() {
        return new MainInfoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_xiang;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {

    }

    @Override
    protected void initView() {
        recView.setLayoutManager(new LinearLayoutManager(this));
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initData() {
        mPresenter.getDataInfo((String) SpUtil.getParam(Constants.TOKEN, ""), getIntent().getIntExtra(Constants.DATA, 0));
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null){
                    popupWindow.dismiss();
                }
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            showLoading();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainDataInfoActivity.this, "成功了", Toast.LENGTH_LONG).show();
            hideLoading();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainDataInfoActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            hideLoading();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainDataInfoActivity.this, "取消了", Toast.LENGTH_LONG).show();
            hideLoading();
        }
    };

    @Override
    public void toastShort(String msg) {

    }
}
