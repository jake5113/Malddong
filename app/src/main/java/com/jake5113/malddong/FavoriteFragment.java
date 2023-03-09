package com.jake5113.malddong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    ArrayList<ToiletItem> favoriteItmes = new ArrayList<>();
    boolean like;
    String photo;
    String toiletNm;
    String rnAdres;
    RecyclerView recyclerView;
    FavoriteRecyclerAdapter adapter;
    ImageView ivFavorite;
    SQLiteDatabase database;

    public void addFavorite(String photo, String toiletNm, String rnAdres) {
        favoriteItmes.add(new ToiletItem(photo, toiletNm, rnAdres));
    }

    public void removeFavorite() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivFavorite = view.findViewById(R.id.iv_favorite);
        recyclerView = view.findViewById(R.id.recyclerview_favorite);
        adapter = new FavoriteRecyclerAdapter(getActivity(), favoriteItmes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        Intent intent = new Intent();
//        if (intent.hasExtra("photo")) {
//            photo = intent.getStringExtra("photo");
//            toiletNm = intent.getStringExtra("toiletNm");
//            rnAdres = intent.getStringExtra("rnAdres");
//            favoriteItmes.add(new ToiletItem(photo, toiletNm, rnAdres));
//        }
//        SharedPreferences pref = getContext().getSharedPreferences("Favorite", Context.MODE_PRIVATE);
//
//        like = pref.getBoolean("like", false);
//        photo = pref.getString("photo", " ");
//        toiletNm = pref.getString("toiletNm", " ");
//        rnAdres = pref.getString("rnAdres", " ");
//        favoriteItmes.add(new ToiletItem(photo, toiletNm, rnAdres));
        database = SQLiteDatabase.openOrCreateDatabase("favorite", null);
        Cursor cursor = database.rawQuery("SELECT * FROM toilet", null);
        if (cursor == null) return;

        int cnt = cursor.getCount();
        cursor.moveToFirst();

        for (int i = 0; i < cnt; i++) {
            photo = cursor.getString(1);
            toiletNm = cursor.getString(2);
            rnAdres = cursor.getString(3);

            favoriteItmes.add(new ToiletItem(photo, toiletNm, rnAdres));
            cursor.moveToNext();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}