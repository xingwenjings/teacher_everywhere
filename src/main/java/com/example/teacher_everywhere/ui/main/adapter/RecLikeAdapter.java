package com.example.teacher_everywhere.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teacher_everywhere.R;
import com.example.teacher_everywhere.bean.LikeBean;
import com.example.teacher_everywhere.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecLikeAdapter extends RecyclerView.Adapter<RecLikeAdapter.ViewHolder> {

    private Context context;
    private List<LikeBean.ResultEntity.CollectedRoutesEntity> list;

    public RecLikeAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<LikeBean.ResultEntity.CollectedRoutesEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context, R.layout.item_follow, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideUtil.loadUrlRoundImg(6, R.mipmap.zhanweitu_home_kapian, R.mipmap.zhanweitu_home_kapian,
                list.get(position).getCardURL(), holder.img, context);
        holder.title.setText(list.get(position).getTitle());
        holder.intro.setText(list.get(position).getIntro());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        TextView intro;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            intro = itemView.findViewById(R.id.intro);
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
}