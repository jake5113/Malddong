package com.jake5113.malddong;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ListFragment extends Fragment {
    ArrayList<ToiletItem> items;
    RecyclerView recyclerView;
    ToiletRecyclerAdapter adapter;
    ImageView ivFavorite, ivLoading;

    public ListFragment(ArrayList<ToiletItem> items) {
        this.items = items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivFavorite = view.findViewById(R.id.iv_favorite);
        recyclerView = view.findViewById(R.id.recyclerview_list);
        ivLoading = view.findViewById(R.id.iv_loading);

        //TODO : 이 부분 해결하기
        adapter = new ToiletRecyclerAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);

        Log.i("LISTFRAGMENT", "onViewCreated");
    }


    @Override
    public void onResume() {
        super.onResume();
        ivLoading.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LISTFRAGMENT", "onCreate");

    }
}