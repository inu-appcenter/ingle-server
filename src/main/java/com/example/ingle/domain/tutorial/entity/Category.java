package com.example.ingle.domain.tutorial.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    CAMPUS_LIFE("Campus Life"), // 캠퍼스 관련 (교통, 기숙사, 도서관)
    LIFE_STYLE("Life Style"), // 생활 관련 (병원, 보험, 알바)
    ACADEMIC_AFFAIRS("Academic Affairs"); // 학생 지원 (등록금, 교과목, 수강신청, 성적)

    private final String description;
}
