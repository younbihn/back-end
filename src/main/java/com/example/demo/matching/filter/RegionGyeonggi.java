package com.example.demo.matching.filter;

public enum RegionGyeonggi {
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

    RegionGyeonggi(String korean){
        this.korean = korean;
    }

    public String getKorean() {
        return this.korean;
    }
}
