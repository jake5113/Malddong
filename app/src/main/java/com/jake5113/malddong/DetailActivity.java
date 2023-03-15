package com.jake5113.malddong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.jake5113.malddong.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ToiletItem toiletItem = (ToiletItem) intent.getSerializableExtra("toiletItem");
        Glide.with(this).load(toiletItem.photo[0]).into(binding.ivToilet);
        binding.toiletNm.setText(toiletItem.toiletNm);
        binding.rnAdres.setText(toiletItem.rnAdres);
        binding.lnmAdres.setText(toiletItem.lnmAdres);
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

    }
}