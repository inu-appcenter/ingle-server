package com.example.ingle.domain.member.enums;

import lombok.Getter;

@Getter
public enum Program {
    EXCHANGE,     // 교환학생
    UNDERGRADUATE, // 학부생
    GRADUATE,     // 대학원생
    OTHER         // 기타
}