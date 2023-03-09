package com.jake5113.malddong;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ToiletRecyclerAdapter extends RecyclerView.Adapter<ToiletRecyclerAdapter.VH> {

    Context context;
    ArrayList<ToiletItem> items;
    SQLiteDatabase database;


    public ToiletRecyclerAdapter(Context context, ArrayList<ToiletItem> items) {
        this.context = context;
        this.items = items;
        database = context.openOrCreateDatabase("favorite", Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS toilet(num INTEGER PRIMARY KEY AUTOINCREMENT, photo TEXT, toiletNm TEXT, rnAdres TEXT)");
    }

    @NonNull
    @Override

    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_toilet, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        //SQLite
        //TODO: DB 오류 발생!

        ToiletItem toiletItem = items.get(position);
        Glide.with(context).load(toiletItem.photo).into(holder.ivImg);
        holder.tvName.setText(toiletItem.toiletNm);
        holder.tvAddr.setText(toiletItem.rnAdres);

        if (toiletItem.like) {
            holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        holder.ivFavorite.setOnClickListener(v -> {
            if (toiletItem.like) {
                // 좋아요 해제
                holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24);

                //SQLite
                database.execSQL("DELETE FROM toilet WHERE toiletNm ="+ "'" + toiletItem.toiletNm+ "'");

                toiletItem.like = !toiletItem.like;

            } else {
                // 좋아요 설정
                holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_24);

                //DB에 저장
                database.execSQL("INSERT INTO toilet (photo, toiletNm, rnAdres) VALUES ('" + toiletItem.photo + "','" + toiletItem.toiletNm + "','" + toiletItem.rnAdres + "')");

                toiletItem.like = !toiletItem.like;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvName, tvAddr;
        ImageView ivImg;
        ImageView ivFavorite;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddr = itemView.findViewById(R.id.tv_addr);
            ivImg = itemView.findViewById(R.id.iv_img);
            ivFavorite = itemView.findViewById(R.id.iv_favorite);
        }
    }
}
