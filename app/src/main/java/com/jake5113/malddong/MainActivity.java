package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.SurfaceControl;

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

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToiletItem> items = new ArrayList<>();
    RequestQueue requestQueue;
    Fragment listFragment, mapFragment, favoriteFragment;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // JSON 파싱
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

        // 리스트 프레그먼트에 api로 불러온 값들 저장.
        listFragment = new ListFragment(items);

        mapFragment = new MapFragment();
        favoriteFragment = new FavoriteFragment();

        tabLayout = findViewById(R.id.tablayout);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, listFragment).commit();
        Objects.requireNonNull(tabLayout.getTabAt(1)).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                if (pos == 0)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, mapFragment).commit();
                else if (pos == 1)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, listFragment).commit();
                else if (pos == 2)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, favoriteFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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