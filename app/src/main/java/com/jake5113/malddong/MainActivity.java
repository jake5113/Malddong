package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    Fragment listFragment;
    TabLayout tabLayout;
    TabItem tabMap, tabList, tabStored;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, listFragment).commit();


        //TODO: 탭버튼 이벤트 처리 (Fragment 전환)


        // JSON 파싱
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "http://apis.data.go.kr/6510000/publicToiletService";
        String key = "wj7oRO6dukW0QCaRyFLL%2FCVQB4H5WztM2mZlRr%2FAeP%2BvRUxW2nABknrxggyD7NHnLaOgARxnjnhDMYQEeCGgzA%3D%3D";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + "Key=" + key, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("item");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        // https://youtu.be/mMzT4fSHU-8?list=PLrnPJCHvNZuBdsuDMl3I-EOEOnCh6JNF3&t=500
                        // TODO: 위 링크 피카소->글라이드 해서 나머지 처리하기()
                        // https://youtu.be/bRvLg27EWp0?list=PLrnPJCHvNZuBdsuDMl3I-EOEOnCh6JNF3&t=330

                    }

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
    }
}