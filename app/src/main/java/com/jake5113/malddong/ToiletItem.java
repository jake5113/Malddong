package com.jake5113.malddong;

public class ToiletItem {
    int img; //sample
    String photoYn, photo; // 사진 유무, 사진
    String toiletNm, opnTimeInfo; // 화장실 명, 개방 시간 정보
    String rnAdres, lnmAdres; // 도로명 주소, 지번 주소
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
            femaleChildClosetCn; // 여성 어린이 대변기 수

    public ToiletItem(String photo, String toiletNm, String rnAdres) {
        this.photo = photo;
        this.toiletNm = toiletNm;
        this.rnAdres = rnAdres;
    }
}
