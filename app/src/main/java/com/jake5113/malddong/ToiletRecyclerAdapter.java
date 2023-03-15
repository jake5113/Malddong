package com.jake5113.malddong;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
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

        ToiletItem toiletItem = items.get(position);
        Glide.with(context).load(toiletItem.photo[0]).into(holder.ivImg);
        holder.tvName.setText(toiletItem.toiletNm);
        holder.tvAddr.setText(toiletItem.rnAdres);

        // 하트 눌러져 있는거 확인 & 뷰 재사용시 다시 비어있는 하트로 설정!!
        toiletItem.like = checkIsLike(toiletItem.toiletNm, toiletItem.like);
        if (toiletItem.like) {
            holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        // 좋아요 버튼 클릭
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
                database.execSQL("INSERT INTO toilet (photo, toiletNm, rnAdres) VALUES ('" + toiletItem.photo[0] + "','" + toiletItem.toiletNm + "','" + toiletItem.rnAdres + "')");

                toiletItem.like = !toiletItem.like;
            }
        });
        
        // view 클릭시 이벤트 처리
        holder.itemView.setOnClickListener(v->{
            // 데이터만 전달하면 됨.

            // 화면전환
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("toiletItem", (Serializable) toiletItem);
            context.startActivity(intent);
            //Toast.makeText(context, toiletItem.toiletNm, Toast.LENGTH_SHORT).show(); // 토스트로 클릭 테스트
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

    private boolean checkIsLike(String name, boolean likeCheck){
        // DB에서 불러오기
        Cursor cursor = database.rawQuery("SELECT * FROM toilet", null);
        if (cursor == null) return false;
        int cnt = cursor.getCount();
        cursor.moveToFirst();

        for (int i = 0; i < cnt; i++) {
            if(name.equals(cursor.getString(2))){
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }
}
