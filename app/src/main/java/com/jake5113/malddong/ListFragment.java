package com.jake5113.malddong;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ListFragment extends Fragment {
    ArrayList<ToiletItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    ToiletRecyclerAdapter adapter;
    RequestQueue requestQueue;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        items.add(new ToiletItem("https://cdn.pixabay.com/photo/2023/02/08/07/32/vietnamese-woman-7775904__340.jpg", "toiletNmtoiletNm", "rnAdres"));
//        items.add(new ToiletItem("https://cdn.pixabay.com/photo/2023/02/08/07/32/vietnamese-woman-7775904__340.jpg", "toiletNmtoiletNm", "rnAdres"));
//        items.add(new ToiletItem("https://cdn.pixabay.com/photo/2023/02/08/07/32/vietnamese-woman-7775904__340.jpg", "toiletNmtoiletNm", "rnAdres"));
        // JSON 파싱
        requestQueue = Volley.newRequestQueue(getContext());
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://apis.data.go.kr/6510000/publicToiletService/getPublicToiletInfoList?";
        String key = "wj7oRO6dukW0QCaRyFLL%2FCVQB4H5WztM2mZlRr%2FAeP%2BvRUxW2nABknrxggyD7NHnLaOgARxnjnhDMYQEeCGgzA%3D%3D";
        String urlkey = "https://apis.data.go.kr/6510000/publicToiletService/getPublicToiletInfoList?serviceKey=wj7oRO6dukW0QCaRyFLL%2FCVQB4H5WztM2mZlRr%2FAeP%2BvRUxW2nABknrxggyD7NHnLaOgARxnjnhDMYQEeCGgzA%3D%3D&numOfRows=20";
        String sample = "https://pixabay.com/api/?key=34140372-05b90480cdafd0bfc84a860f5&image_type=photo&per_page=200";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, sample, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);

                                // TODO: Sample -> url 로 바꾸기.

                                // sample
                                // String creatorName = item.getString("user");
                                 String imageUrl = item.getString("webformatURL");

                                // String photo = item.getString("photo");
                                 String toiletNm = item.getString("type");
                                 String rnAdres = item.getString("tags");

                                items.add(new ToiletItem(imageUrl, toiletNm, rnAdres));
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