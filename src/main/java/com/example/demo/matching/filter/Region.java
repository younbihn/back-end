package com.example.demo.matching.filter;

public enum Region {
    // 서울
    GANGNAM("강남구"),
    GANGDONG("강동구"),
    GANGBUK("강북구"),
    GANGSEO("강서구"),
    GWANAK("관악구"),
    GWANGJIN("광진구"),
    GURO("구로구"),
    GEUMCHEON("금천구"),
    NOWON("노원구"),
    DOBONG("도봉구"),
    DONGDAEMUN("동대문구"),
    DONGJAK("동작구"),
    MAPO("마포구"),
    SEODAEMUN("서대문구"),
    SEOCHO("서초구"),
    SEONGDONG("성동구"),
    SEONGBUK("성북구"),
    SONGPA("송파구"),
    YANGCHEON("양천구"),
    YEONGDEUNGPO("영등포구"),
    YONGSAN("용산구"),
    EUNPYEONG("은평구"),
    JONGRO("종로구"),
    JUNG("중구"),
    JUNGNANG("중랑구"),
    // 경기도
    SUWON("수원시"),
    YONGIN("용인시"),
    SEONGNAM("성남시"),
    BUCHEON("부천시"),
    HWASEONG("화성시"),
    ANSAN("안산시"),
    ANYANG("안양시"),
    PYEONGTAEK("평택시"),
    SIHEUNG("시흥시"),
    GIMPO("김포시"),
    GWANGJU("광주시"),
    GWANGMYEONG("광명시"),
    GUNPO("군포시"),
    HANAM("하남시"),
    OSAN("오산시"),
    ICHEON("이천시"),
    ANSEONG("안성시"),
    UIWANG("의왕시"),
    YANGPYEONG("양평군"),
    YEOJU("여주시"),
    GWACHEON("과천시"),
    GOYANG("고양시"),
    NAMYANGJU("남양주시"),
    PAJU("파주시"),
    UIJEONGBU("의정부시"),
    YANGJU("양주시"),
    GURI("구리시"),
    POCHEON("포천시"),
    DONGDUCHEON("동두천시"),
    GAPYEONG("가평군"),
    YEONCHEON("연천군");

    private String korean;

    Region(String korean){
        this.korean = korean;
    }

    public String getKorean() {
        return this.korean;
    }
}
