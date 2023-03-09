package com.jake5113.malddong;


import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    ArrayList<ToiletItem> favoriteItmes;
    boolean like;
    String photo;
    String toiletNm;
    String rnAdres;
    RecyclerView recyclerView;
    FavoriteRecyclerAdapter adapter;
    ImageView ivFavLoading;
    TextView tvFavLoading;
    SQLiteDatabase database;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFavLoading = view.findViewById(R.id.tv_fav_loading);
        ivFavLoading = view.findViewById(R.id.iv_fav_loading);
        recyclerView = view.findViewById(R.id.recyclerview_favorite);
        adapter = new FavoriteRecyclerAdapter(getActivity(), favoriteItmes);
        recyclerView.setAdapter(adapter);

        if (favoriteItmes.size() != 0) {
            ivFavLoading.setVisibility(View.GONE);
            tvFavLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
    }

    private void getData() {
        database = getActivity().openOrCreateDatabase("favorite", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS toilet(num INTEGER PRIMARY KEY AUTOINCREMENT, photo TEXT, toiletNm TEXT, rnAdres TEXT)");

        Cursor cursor = database.rawQuery("SELECT * FROM toilet", null);

        if (cursor == null) return;

        int cnt = cursor.getCount();
        cursor.moveToFirst();

        //TODO 질문하기!!! 매우 안좋은 코드같다고 생각됨.
        favoriteItmes = new ArrayList<>();

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