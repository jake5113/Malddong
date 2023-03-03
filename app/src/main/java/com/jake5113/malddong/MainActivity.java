package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    Fragment listFragment;
    TabLayout tabLayout;
    TabItem tabMap, tabList, tabStored;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, listFragment).commit();


        //TODO: 탭버튼 이벤트 처리 (Fragment 전환)

    }
}