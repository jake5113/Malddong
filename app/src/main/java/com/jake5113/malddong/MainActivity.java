package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.SurfaceControl;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Fragment listFragment, mapFragment;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new ListFragment();
        mapFragment = new MapFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, listFragment).commit();

        tabLayout = findViewById(R.id.tablayout);
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, listFragment).commit(); // 찜화면 구현하기
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