package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.naver.maps.map.MapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToiletItem> items = new ArrayList<>();
    RequestQueue requestQueue;
    Fragment listFragment, favoriteFragment;
    MyMapFragment myMapFragment;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Detail Activity 테스트 버튼
//        findViewById(R.id.testbtn).setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
//            startActivity(intent);
//        });

        // JSON 파싱
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Log.i("MAINACTIVITY", "onCreate");

        // 리스트 프레그먼트에 api로 불러온 값들 저장.
        listFragment = new ListFragment(items);
        myMapFragment = new MyMapFragment();
        favoriteFragment = new FavoriteFragment();

        tabLayout = findViewById(R.id.tablayout);
        Log.i("MAINACTIVITY", "LISTFRAGMENT 생성전");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, listFragment).commit();
        Objects.requireNonNull(tabLayout.getTabAt(1)).select();

        Log.i("MAINACTIVITY", "LISTFRAGMENT 생성후");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                if (pos == 0)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, myMapFragment).commit();
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url + "?serviceKey=" + key + "&pageNo=1&numOfRows=500", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonResponseObject = response.getJSONObject("response");
                            JSONObject jsonMBodyObject = jsonResponseObject.getJSONObject("body");
                            JSONObject jsonItemsObject = jsonMBodyObject.getJSONObject("items");
                            JSONArray jsonArray = jsonItemsObject.getJSONArray("item");

                            Gson gson = new Gson();
                            ToiletItem[] item = gson.fromJson(jsonArray.toString(), ToiletItem[].class);
                            for (int i = 0; i < item.length; i++) {
                                if(item[i].photoYn.equals("Y")) items.add(item[i]);
                            }
                            Log.i("LOOOOOG", item[0].photo.toString());
                            //Json 작업 --> Gson
/*                            JSONObject jsonResponseObject = response.getJSONObject("response");
                            JSONObject jsonMBodyObject = jsonResponseObject.getJSONObject("body");
                            JSONObject jsonItemsObject = jsonMBodyObject.getJSONObject("items");
                            JSONArray jsonArray = jsonItemsObject.getJSONArray("item");

                            //if (items.size() == 0) { //괜찮은 코드인가?
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                String toiletNm = item.getString("toiletNm");
                                String rnAdres = item.getString("rnAdres");
                                try {
                                    if (item.getJSONArray("photo").getString(0) != null) {
                                        String[] photo = new String[5];
                                        photo[0] = item.getJSONArray("photo").getString(0);
                                        items.add(new ToiletItem(photo, toiletNm, rnAdres));
                                    }
                                } catch (JSONException e) {
                                    continue;
                                }
                            }*/
                            // 쓰레드 작업이 끝난 후 notify()
                            ((ListFragment) listFragment).adapter.notifyDataSetChanged();
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