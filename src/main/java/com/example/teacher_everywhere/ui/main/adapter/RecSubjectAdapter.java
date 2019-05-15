package com.example.teacher_everywhere.ui.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.bean.BundleBean;
import com.example.teacher_everywhere.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecSubjectAdapter extends RecyclerView.Adapter<RecSubjectAdapter.ViewHolder> {
    private List<BundleBean.ResultBean.BundlesBean> list;
    private Context context;

    public RecSubjectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<BundleBean.ResultBean.BundlesBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context,R.layout.layout_bundle,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecSubjectAdapter.ViewHolder holder, int position) {
        final BundleBean.ResultBean.BundlesBean bundlesBean = list.get(position);
        GlideUtil.loadUrlImage(R.mipmap.zhanweitu_home_kapian,R.mipmap.zhanweitu_home_kapian,
                bundlesBean.getCardURL(),holder.bigImg,context);

        holder.bigImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBundleClickListener!=null){
                    onBundleClickListener.onClick(bundlesBean.getContentURL(),bundlesBean.getTitle());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView bigImg;
        public ViewHolder(View itemView) {
            super(itemView);
            bigImg=itemView.findViewById(R.id.bg_img);
        }
    }

    private OnBundleClickListener onBundleClickListener;

    public void setOnBundleClickListener(OnBundleClickListener onBundleClickListener) {
        this.onBundleClickListener = onBundleClickListener;
    }

    public interface OnBundleClickListener{
        void onClick(String url, String title);
    }
}
