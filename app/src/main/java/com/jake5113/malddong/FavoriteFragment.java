package com.jake5113.malddong;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    ArrayList<ToiletItem> favoriteItmes = new ArrayList<>();
    boolean like;
    String photo;
    String toiletNm;
    String rnAdres;
    RecyclerView recyclerView;
    FavoriteRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO : 이 부분이 문제라고 예상중 Glide??
        SharedPreferences pref = getActivity().getSharedPreferences("Favorite", Context.MODE_PRIVATE);


        like = pref.getBoolean("like", false);
        photo = pref.getString("photo", " ");
        toiletNm = pref.getString("toiletNm", " ");
        rnAdres = pref.getString("rnAdres", " ");
        favoriteItmes.add(new ToiletItem(photo, toiletNm, rnAdres));
        adapter = new FavoriteRecyclerAdapter(getActivity(), favoriteItmes);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}