package com.jake5113.malddong;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jake5113.malddong.databinding.FragmentMyMapBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {
    FragmentMyMapBinding binding;
    FragmentManager fm;
    MapFragment mapFragment;
    Marker marker;
    LatLng latLng;
    InfoWindow infoWindow;
    CameraUpdate cameraUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fm = requireActivity().getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map);

        mapFragment = MapFragment.newInstance();
        fm.beginTransaction().add(R.id.map, mapFragment).commit();

        mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setExtent(new LatLngBounds(new LatLng(31.43, 122.37), new LatLng(44.35, 132)));
        marker = new Marker();
        latLng = new LatLng(33.485404, 126.481563);
        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(requireContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "내 위치";
            }
        });
        marker.setPosition(latLng);
        cameraUpdate = CameraUpdate.scrollTo(latLng);
        naverMap.moveCamera(cameraUpdate);
        marker.setMap(naverMap);
        infoWindow.open(marker);
    }
}
