package com.jake5113.malddong;

import java.io.Serializable;

public class ToiletItem implements Serializable{
    boolean like = false; // 좋아요 클릭 값 저장
    String photoYn;// 사진 유무, 사진
    String[] photo;
    String toiletNm, opnTimeInfo; // 화장실 명, 개방 시간 정보
    String rnAdres, lnmAdres; // 도로명 주소, 지번 주소
    String emdNm; //읍면동명
    String laCrdnt, loCrdnt; // 경도 위도
    String mngrInsttNm, telno, toiletPosesnSeNm; // 관리 기관 명, 전화번호, 화장실 소유 구분 명
    String maleClosetCnt,  // 남성 대변기 수
            maleUrinalCnt, // 남성 소변기 수
            maleDspsnClosetCnt, // 남성 장애인 대변기 수
            maleDspsnUrinalCnt, // 남성 장애인 소변기 수
            maleChildClosetCnt, // 남성 어린이 대변기 수
            maleChildUrinalCnt, // 남성 어린이 소변기 수
            femaleClosetCnt, // 여성 대변기 수
            femaleDspsnClosetCnt, // 여성 장애인 대변기 수
            femaleChildClosetCnt; // 여성 어린이 대변기 수

    public ToiletItem() {
    }

    public ToiletItem(String[] photo, String toiletNm, String rnAdres) {
        this.photo = photo;
        this.toiletNm = toiletNm;
        this.rnAdres = rnAdres;
    }

    public ToiletItem(boolean like, String photoYn, String[] photo, String toiletNm, String opnTimeInfo, String rnAdres, String lnmAdres, String emdNm, String laCrdnt, String loCrdnt, String mngrInsttNm, String telno, String toiletPosesnSeNm, String maleClosetCnt, String maleUrinalCnt, String maleDspsnClosetCnt, String maleDspsnUrinalCnt, String maleChildClosetCnt, String maleChildUrinalCnt, String femaleClosetCnt, String femaleDspsnClosetCnt, String femaleChildClosetCnt) {
        this.like = like;
        this.photoYn = photoYn;
        this.photo = photo;
        this.toiletNm = toiletNm;
        this.opnTimeInfo = opnTimeInfo;
        this.rnAdres = rnAdres;
        this.lnmAdres = lnmAdres;
        this.emdNm = emdNm;
        this.laCrdnt = laCrdnt;
        this.loCrdnt = loCrdnt;
        this.mngrInsttNm = mngrInsttNm;
        this.telno = telno;
        this.toiletPosesnSeNm = toiletPosesnSeNm;
        this.maleClosetCnt = maleClosetCnt;
        this.maleUrinalCnt = maleUrinalCnt;
        this.maleDspsnClosetCnt = maleDspsnClosetCnt;
        this.maleDspsnUrinalCnt = maleDspsnUrinalCnt;
        this.maleChildClosetCnt = maleChildClosetCnt;
        this.maleChildUrinalCnt = maleChildUrinalCnt;
        this.femaleClosetCnt = femaleClosetCnt;
        this.femaleDspsnClosetCnt = femaleDspsnClosetCnt;
        this.femaleChildClosetCnt = femaleChildClosetCnt;
    }
}
