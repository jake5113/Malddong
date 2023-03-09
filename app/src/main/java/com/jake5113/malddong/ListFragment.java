package com.jake5113.malddong;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
    ArrayList<ToiletItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    ToiletRecyclerAdapter adapter;
    RequestQueue requestQueue;
    ImageView ivFavorite, ivLoading;


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
    }

    @Override
    public void onResume() {
        super.onResume();
        ivLoading.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // JSON 파싱
        requestQueue = Volley.newRequestQueue(requireContext());

        // Fragment가 onCreate 될 때마다 파싱이 되는 문제점.
        parseJSON();

    }

    private void parseJSON() {
        String url = "http://apis.data.go.kr/6510000/publicToiletService/getPublicToiletInfoList";
        String key = "wj7oRO6dukW0QCaRyFLL%2FCVQB4H5WztM2mZlRr%2FAeP%2BvRUxW2nABknrxggyD7NHnLaOgARxnjnhDMYQEeCGgzA%3D%3D";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + "?serviceKey=" + key + "&pageNo=1&numOfRows=200", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonResponseObject = response.getJSONObject("response");
                            JSONObject jsonMBodyObject = jsonResponseObject.getJSONObject("body");
                            JSONObject jsonItemsObject = jsonMBodyObject.getJSONObject("items");
                            JSONArray jsonArray = jsonItemsObject.getJSONArray("item");

                            if (items.size() == 0) { //괜찮은 코드인가?
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);
                                    String toiletNm = item.getString("toiletNm");
                                    String rnAdres = item.getString("rnAdres");
                                    try {
                                        if (item.getJSONArray("photo").getString(0) != null) {
                                            String photo = item.getJSONArray("photo").getString(0);
                                            items.add(new ToiletItem(photo, toiletNm, rnAdres));
                                        }
                                    } catch (JSONException e) {
                                        continue;
                                    }
                                }
                            }
                            adapter = new ToiletRecyclerAdapter(getActivity(), items);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}