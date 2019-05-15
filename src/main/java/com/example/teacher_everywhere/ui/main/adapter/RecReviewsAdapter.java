package com.example.teacher_everywhere.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.bean.MainDataInfo;
import com.example.teacher_everywhere.util.GlideUtil;

import java.util.ArrayList;

public class RecReviewsAdapter extends RecyclerView.Adapter<RecReviewsAdapter.ViewHolder> {
    private ArrayList<MainDataInfo.ResultEntity.ReviewsEntity> reviewsEntities;
    private Context context;

    public RecReviewsAdapter(Context context) {
        this.context = context;
        reviewsEntities=new ArrayList<>();
    }

    public void setReviewsEntities(ArrayList<MainDataInfo.ResultEntity.ReviewsEntity> reviewsEntities) {
        this.reviewsEntities = reviewsEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context,R.layout.rec_reviews_adapter,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecReviewsAdapter.ViewHolder holder, int position) {
        if (position==getItemCount()-1){
            holder.divider.setVisibility(View.GONE);
        }else {
            holder.divider.setVisibility(View.VISIBLE);
        }
        holder.tv_name.setText(reviewsEntities.get(position).getUserName());
        holder.tv_content.setText(reviewsEntities.get(position).getContent());
        holder.tvs_data.setText(reviewsEntities.get(position).getCreatedAt());
        GlideUtil.loadUrlCircleImage(R.mipmap.zhanweitu_touxiang,R.mipmap.zhanweitu_touxiang,
                reviewsEntities.get(position).getUserPhoto(),holder.iv_header,context);
        if (reviewsEntities.get(position).getImages()!=null&&reviewsEntities.get(position).getImages().size()>0){
            holder.rec_imgs.setLayoutManager(new GridLayoutManager(context,3));
            RecImageAdapter recImageAdapter = new RecImageAdapter(context, reviewsEntities.get(position).getImages());
            holder.rec_imgs.setAdapter(recImageAdapter);
            recImageAdapter.setOnImageClickListener(new RecImageAdapter.OnImageClickListener() {
                @Override
                public void onClick(String imgUrl) {
                    onImgClickListener.onClick(imgUrl);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return reviewsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_header;
        private TextView tv_name;
        private TextView tvs_data;
        private TextView tv_content;
        private RecyclerView rec_imgs;
        private View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_header=itemView.findViewById(R.id.iv_header);
            tv_name = itemView.findViewById(R.id.tv_name);
            tvs_data=itemView.findViewById(R.id.tvs_date);
            tv_content = itemView.findViewById(R.id.tv_content);
            rec_imgs = itemView.findViewById(R.id.rec_imgs);
            divider = itemView.findViewById(R.id.divider);

        }
    }
    private OnImgClickListener onImgClickListener;

    public void setOnImgClickListener(OnImgClickListener onImgClickListener) {
        this.onImgClickListener = onImgClickListener;
    }

    public interface OnImgClickListener{
        void onClick(String imgUrl);
    }
}
