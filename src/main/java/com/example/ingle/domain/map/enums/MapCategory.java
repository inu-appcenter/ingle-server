package com.example.ingle.domain.map.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MapCategory {

    SCHOOL_BUILDING("School Building"),             // 학교 건물
    RESTAURANT("Restaurant"),                       // 식당
    CAFE("Cafe"),                                   // 카페
    CONVENIENCE_STORE("Convenience Store"),         // 편의점
    BUS_STOP("Bus Stop"),                           // 버스 정류장
    SMOKING_BOOTH("Smoking Booth");                 // 흡연부스

    private final String description;
}