package com.example.teacher_everywhere.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.bean.BanmiInfo;
import com.example.teacher_everywhere.util.GlideUtil;

import java.util.List;

public class RecDynamicAdapter extends RecyclerView.Adapter {
    private List<BanmiInfo.ResultEntity.ActivitiesEntity> resultEntities;
    private Context context;
    private final int MODE_IMG=0;
    private final int BIN_IMG=1;

    public RecDynamicAdapter(List<BanmiInfo.ResultEntity.ActivitiesEntity> resultEntities, Context context) {
        this.resultEntities = resultEntities;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MODE_IMG){
            return new MoreImgHolder(View.inflate(context,R.layout.item_banmi_xiang_moreimg,null));
        }else {
            return new BigImgHoler(View.inflate(context,R.layout.item_banmi_xiang,null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BanmiInfo.ResultEntity.ActivitiesEntity activitiesEntity = resultEntities.get(position);
        if (getItemViewType(position)==BIN_IMG){
            BigImgHoler bigImgHoler= (BigImgHoler) holder;
            bigImgHoler.tvData.setText(activitiesEntity.getDate());
            bigImgHoler.tvTitle.setText(activitiesEntity.getContent());
            bigImgHoler.tvPraiseCount.setText(activitiesEntity.getLikeCount()+"");
            bigImgHoler.tvReplyCount.setText(activitiesEntity.getReplyCount()+"");
            if (activitiesEntity.isIsLiked()){
                GlideUtil.loadResImage(R.mipmap.praise_unselected,R.mipmap.praise_unselected,R.mipmap.praise,bigImgHoler.ivPraise,context);
            }
            if (activitiesEntity.getImages().size()==1){
                GlideUtil.loadUrlImage(R.mipmap.zhanweitu_touxiang,R.mipmap.zhanweitu_touxiang,activitiesEntity.getImages().get(0),bigImgHoler.bigImg,context);
                bigImgHoler.bigImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onImageClickListener!=null){
                            onImageClickListener.onClick(activitiesEntity.getImages().get(0));
                        }
                    }
                });
            }
        }else {
            MoreImgHolder moreImgHolder = (MoreImgHolder) holder;
            moreImgHolder.tvDate.setText(activitiesEntity.getDate());
            moreImgHolder.tvTitle.setText(activitiesEntity.getContent());
            moreImgHolder.tvPraiseCount.setText(activitiesEntity.getLikeCount()+"");
            moreImgHolder.tvReplyCount.setText(activitiesEntity.getReplyCount()+"");
            if (activitiesEntity.isIsLiked()){
                GlideUtil.loadResImage(R.mipmap.praise_unselected,R.mipmap.praise_unselected,R.mipmap.praise,moreImgHolder.ivPraise,context);
            }
            moreImgHolder.recView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL,false));
            RecImageAdapter adapter = new RecImageAdapter(context, activitiesEntity.getImages());
            moreImgHolder.recView.setAdapter(adapter);
            adapter.setOnImageClickListener(new RecImageAdapter.OnImageClickListener() {
                @Override
                public void onClick(String imgUrl) {
                    if (onImageClickListener != null){
                        onImageClickListener.onClick(imgUrl);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return resultEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (resultEntities.get(position).getImages().size()>1){
            return MODE_IMG;
        }else {
            return BIN_IMG;
        }
    }
    class BigImgHoler extends RecyclerView.ViewHolder{
        TextView tvData;
        TextView tvTitle;
        TextView tvIntro;
        ImageView bigImg;
        TextView tvReplyCount;
        TextView tvPraiseCount;
        ImageView ivPraise;
        public BigImgHoler(View itemView) {
            super(itemView);
            tvData=itemView.findViewById(R.id.tv_data);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvIntro = itemView.findViewById(R.id.tv_intro);
            tvReplyCount = itemView.findViewById(R.id.tv_reply_count);
            tvPraiseCount = itemView.findViewById(R.id.tv_praise_count);
            bigImg = itemView.findViewById(R.id.iv_big);
            ivPraise = itemView.findViewById(R.id.iv_praise);
        }
    }
    class MoreImgHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvTitle;
        TextView tvIntro;
        RecyclerView recView;
        TextView tvReplyCount;
        TextView tvPraiseCount;
        ImageView ivPraise;
        public MoreImgHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.iv_data);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvIntro = itemView.findViewById(R.id.tv_intro);
            tvReplyCount = itemView.findViewById(R.id.tv_reply_count);
            tvPraiseCount = itemView.findViewById(R.id.tv_praise_count);
            recView = itemView.findViewById(R.id.recView);
            ivPraise = itemView.findViewById(R.id.iv_praise);
        }
    }
    private OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener{
        void onClick(String imgUrl);
    }
}
