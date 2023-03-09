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
        database = SQLiteDatabase.openOrCreateDatabase("favorite", null);
        database.execSQL("CREATE TABLE IF NOT EXISTS toilet(num INTEGER PRIMARY KEY AUTOINCREMENT, photo TEXT, toiletNm TEXT, rnAdres TEXT)");


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
                //database.execSQL("DELETE FROM toilet WHERE toiletNm = " + toiletItem.toiletNm);

                // SharedPreferences 데이터 삭제하기

                toiletItem.like = !toiletItem.like;
            } else {
                // 좋아요 설정
                holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_24);

                //DB에 저장
                //database.execSQL("INSERT INTO toilet (photo, toiletNm, rnAdres) VALUES ('" + toiletItem.photo + "','" + toiletItem.toiletNm + "','" + toiletItem.rnAdres + "')");

                //TEST
                //TODO: Arraylist 값 전달
//                FavoriteFragment favoriteFragment = new FavoriteFragment();
//                favoriteFragment.addFavorite(toiletItem.photo,toiletItem.toiletNm,toiletItem.rnAdres);

//                Intent intent = new Intent();
//                intent.putExtra("photo", toiletItem.photo);
//                intent.putExtra("toiletNm", toiletItem.toiletNm);
//                intent.putExtra("rnAdres", toiletItem.rnAdres);


                // TODO: SharedPreferences 로 저장하기 // 실패한 이유 : 값이 대체된다..!
//                SharedPreferences pref = context.getSharedPreferences("Favorite", Context.MODE_PRIVATE);
//                // 저장작업 시작
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putBoolean("like", toiletItem.like);
//                editor.putString("photo", toiletItem.photo);
//                editor.putString("toiletNm", toiletItem.toiletNm);
//                editor.putString("rnAdres", toiletItem.rnAdres);
//                editor.apply();// commit()?

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
