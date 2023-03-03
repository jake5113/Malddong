package com.jake5113.malddong;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToiletRecyclerAdapter extends RecyclerView.Adapter<ToiletRecyclerAdapter.VH> {

    Context context;
    ArrayList<ToiletItem> items;

    public ToiletRecyclerAdapter(Context context, ArrayList<ToiletItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override

    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_toilet, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ToiletItem toiletItem = items.get(position);
        holder.ivImg.setImageResource(toiletItem.img);
        holder.tvName.setText(toiletItem.toiletNm);
        holder.tvAddr.setText(toiletItem.rnAdres);
    }

    @Override
    public int getItemCount() {return items.size();}

    class VH extends RecyclerView.ViewHolder {

        TextView tvName, tvAddr;
        ImageView ivImg;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddr = itemView.findViewById(R.id.tv_addr);
            ivImg = itemView.findViewById(R.id.iv_img);
        }
    }
}
