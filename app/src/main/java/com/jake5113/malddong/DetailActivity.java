package com.jake5113.malddong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.jake5113.malddong.databinding.ActivityDetailBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityDetailBinding binding;
    LatLng latLng;
    String toiletNm = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.detail_map);

        mapFragment = MapFragment.newInstance();
        fm.beginTransaction().replace(R.id.detail_map, mapFragment).commit();

        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        ToiletItem toiletItem = (ToiletItem) intent.getSerializableExtra("toiletItem");
        Glide.with(this).load(toiletItem.photo[0]).into(binding.ivToilet);
        toiletNm = toiletItem.toiletNm;
        binding.toiletNm.setText(toiletNm);
        binding.rnAdres.setText(toiletItem.rnAdres);
        binding.lnmAdres.setText(toiletItem.lnmAdres);
        binding.opnTimeInfo.setText(toiletItem.opnTimeInfo);
        binding.mngrInsttNm.setText("관리 기관:" + toiletItem.mngrInsttNm);
        binding.toiletPosesnSeNm.setText("화장실 소유:" + toiletItem.toiletPosesnSeNm);
        binding.telno.setText("전화번호:" + toiletItem.telno);
        binding.maleClosetCnt.setText("남성 대변기 수:" + toiletItem.maleClosetCnt);
        binding.maleUrinalCnt.setText("남성 소변기 수:" + toiletItem.maleUrinalCnt);
        binding.maleDspsnClosetCnt.setText("남성 장애인 대변기 수:" + toiletItem.maleDspsnClosetCnt);
        binding.maleDspsnUrinalCnt.setText("남성 장애인 소변기 수:" + toiletItem.maleDspsnUrinalCnt);
        binding.maleChildClosetCnt.setText("남성 어린이 대변기 수:" + toiletItem.maleChildClosetCnt);
        binding.maleChildUrinalCnt.setText("남성 어린이 소변기 수:" + toiletItem.maleChildUrinalCnt);
        binding.femaleClosetCnt.setText("여성 대변기 수:" + toiletItem.femaleClosetCnt);
        binding.femaleDspsnClosetCnt.setText("여성 장애인 대변기 수:" + toiletItem.femaleDspsnClosetCnt);
        binding.femaleChildClosetCnt.setText("여성 어린이 대변기 수:" + toiletItem.femaleChildClosetCnt);
        latLng = new LatLng(Double.parseDouble(toiletItem.laCrdnt), Double.parseDouble(toiletItem.loCrdnt));
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Marker marker = new Marker();
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return toiletNm;
            }
        });
        infoWindow.setPosition(latLng);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng);
        naverMap.moveCamera(cameraUpdate);
        marker.setMap(null);
        infoWindow.setAlpha(0.8f);
        infoWindow.open(naverMap);
    }
}