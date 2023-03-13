package com.jake5113.malddong;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.VH> {

    Context context;
    ArrayList<ToiletItem> items;
    SQLiteDatabase database;
    FavoriteFragment favoriteFragment;

    public FavoriteRecyclerAdapter(Context context, ArrayList<ToiletItem> items, FavoriteFragment favoriteFragment) {
        this.context = context;
        this.items = items;
        this.favoriteFragment = favoriteFragment;

        // TODO : 데이터베이스 너무 중복되는 코드가 많음. 수정 필요.
        database = context.openOrCreateDatabase("favorite", Context.MODE_PRIVATE, null);
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
        Glide.with(context).load(toiletItem.photo).into(holder.ivImg);
        holder.tvName.setText(toiletItem.toiletNm);
        holder.tvAddr.setText(toiletItem.rnAdres);

        // 수정 필요한 코드 - DB값 활용해야 함.
        holder.ivFavorite.setImageResource(R.drawable.baseline_favorite_24);

        holder.ivFavorite.setOnClickListener(v -> {
            database.execSQL("DELETE FROM toilet WHERE toiletNm =" + "'" + toiletItem.toiletNm + "'");
            items.remove(toiletItem);
            // notify??? 흠...
            //TODO: Fragment refresh 가 필요함!
            favoriteFragment.adapter.notifyDataSetChanged();

            if (favoriteFragment.favoriteItmes.size() == 0) {
                favoriteFragment.ivFavLoading.setVisibility(View.VISIBLE);
                favoriteFragment.tvFavLoading.setVisibility(View.VISIBLE);
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
