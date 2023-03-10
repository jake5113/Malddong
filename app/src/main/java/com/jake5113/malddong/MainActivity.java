package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.SurfaceControl;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Fragment listFragment, mapFragment, favoriteFragment;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        favoriteFragment = new FavoriteFragment();

        tabLayout = findViewById(R.id.tablayout);

        //TODO: 스마트폰 회전 시 View가 재생성되면서 다시 호출되는 문제 해결해야 함.
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
}