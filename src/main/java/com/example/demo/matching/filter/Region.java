package com.example.demo.matching.filter;

public enum Region {
    // 서울
    GANGNAM("서울시 강남구"),
    GANGDONG("서울시 강동구"),
    GANGBUK("서울시 강북구"),
    GANGSEO("서울시 강서구"),
    GWANAK("서울시 관악구"),
    GWANGJIN("서울시 광진구"),
    GURO("서울시 구로구"),
    GEUMCHEON("서울시 금천구"),
    NOWON("서울시 노원구"),
    DOBONG("서울시 도봉구"),
    DONGDAEMUN("서울시 동대문구"),
    DONGJAK("서울시 동작구"),
    MAPO("서울시 마포구"),
    SEODAEMUN("서울시 서대문구"),
    SEOCHO("서울시 서초구"),
    SEONGDONG("서울시 성동구"),
    SEONGBUK("서울시 성북구"),
    SONGPA("서울시 송파구"),
    YANGCHEON("서울시 양천구"),
    YEONGDEUNGPO("서울시 영등포구"),
    YONGSAN("서울시 용산구"),
    EUNPYEONG("서울시 은평구"),
    JONGRO("서울시 종로구"),
    JUNG("서울시 중구"),
    JUNGNANG("서울시 중랑구"),
    // 경기도
    SUWON("경기도 수원시"),
    YONGIN("경기도 용인시"),
    SEONGNAM("경기도 성남시"),
    BUCHEON("경기도 부천시"),
    HWASEONG("경기도 화성시"),
    ANSAN("경기도 안산시"),
    ANYANG("경기도 안양시"),
    PYEONGTAEK("경기도 평택시"),
    SIHEUNG("경기도 시흥시"),
    GIMPO("경기도 김포시"),
    GWANGJU("경기도 광주시"),
    GWANGMYEONG("경기도 광명시"),
    GUNPO("경기도 군포시"),
    HANAM("경기도 하남시"),
    OSAN("경기도 오산시"),
    ICHEON("경기도 이천시"),
    ANSEONG("경기도 안성시"),
    UIWANG("경기도 의왕시"),
    YANGPYEONG("경기도 양평군"),
    YEOJU("경기도 여주시"),
    GWACHEON("경기도 과천시"),
    GOYANG("경기도 고양시"),
    NAMYANGJU("경기도 남양주시"),
    PAJU("경기도 파주시"),
    UIJEONGBU("경기도 의정부시"),
    YANGJU("경기도 양주시"),
    GURI("경기도 구리시"),
    POCHEON("경기도 포천시"),
    DONGDUCHEON("경기도 동두천시"),
    GAPYEONG("경기도 가평군"),
    YEONCHEON("경기도 연천군");

    private String korean;

    Region(String korean){
        this.korean = korean;
    }

    public String getKorean() {
        return this.korean;
    }
}
