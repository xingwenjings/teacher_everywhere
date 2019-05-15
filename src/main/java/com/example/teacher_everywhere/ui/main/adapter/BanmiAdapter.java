package com.example.teacher_everywhere.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.bean.BanmiBean;
import com.example.teacher_everywhere.util.GlideUtil;
import com.example.teacher_everywhere.util.ToastUtil;

import java.util.ArrayList;

public class BanmiAdapter extends RecyclerView.Adapter<BanmiAdapter.ViewHolder> {
    private ArrayList<BanmiBean.ResultEntity.BanmiEntity> banmiBeans;
    private Context context;

    public BanmiAdapter(Context context) {
        this.context = context;
        banmiBeans = new ArrayList<>();
    }


    public void setList(ArrayList<BanmiBean.ResultEntity.BanmiEntity> banmiBean) {
        this.banmiBeans = banmiBean;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stay, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        GlideUtil.loadUrlImage(R.mipmap.zhanweitu_home_kapian,R.mipmap.zhanweitu_home_kapian,
                banmiBeans.get(i).getPhoto(),viewHolder.img,context);
        if (banmiBeans.get(i).isIsFollowed()){
            GlideUtil.loadResImage(R.mipmap.zhanweitu_home_kapian,R.mipmap.zhanweitu_home_kapian,
                    R.mipmap.follow,viewHolder.ivFollow,context);
        }else {
            GlideUtil.loadResImage(R.mipmap.zhanweitu_home_kapian,R.mipmap.zhanweitu_home_kapian,
                    R.mipmap.follow_unselected,viewHolder.ivFollow,context);
        }
        viewHolder.tvName.setText(banmiBeans.get(i).getName());
        viewHolder.tvFollowing.setText(banmiBeans.get(i).getFollowing()+"人关注");
        viewHolder.tvOccupation.setText(banmiBeans.get(i).getOccupation());
        viewHolder.tvCity.setText(banmiBeans.get(i).getLocation());
        viewHolder.ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (banmiBeans.get(i).isIsFollowed()){
                    if (onFollowCliclListener != null){
                        ToastUtil.showShort("已取消关注");
                        onFollowCliclListener.cancelFollow(banmiBeans.get(i).getId());
                        Glide.with(context).load(R.mipmap.follow_unselected).into(viewHolder.ivFollow);
                        banmiBeans.get(i).setIsFollowed(false);
                    }
                }else {
                    if (onFollowCliclListener != null){
                        ToastUtil.showShort("已关注");
                        onFollowCliclListener.onFollow(banmiBeans.get(i).getId());
                        Glide.with(context).load(R.mipmap.follow).into(viewHolder.ivFollow);
                        banmiBeans.get(i).setIsFollowed(true);
                    }
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onClick(banmiBeans.get(i));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return banmiBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvFollowing;
        TextView tvCity;
        TextView tvOccupation;
        ImageView ivFollow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvFollowing = itemView.findViewById(R.id.tv_following);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvOccupation = itemView.findViewById(R.id.tv_occupation);
            ivFollow = itemView.findViewById(R.id.iv_follow);
        }
    }
    private OnFollowCliclListener onFollowCliclListener;

    public void setOnFollowCliclListener(OnFollowCliclListener onFollowCliclListener) {
        this.onFollowCliclListener = onFollowCliclListener;
    }

    public interface OnFollowCliclListener{
        void onFollow(int id);
        void cancelFollow(int id);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(BanmiBean.ResultEntity.BanmiEntity entity);
    }


}
