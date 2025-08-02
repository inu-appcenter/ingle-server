package com.example.ingle.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Program {

    UNDERGRADUATE("Undergraduate Student"),    // 학부생
    EXCHANGE("Exchange Student"),              // 교환학생
    GRADUATE("Graduate Student"),              // 대학원생
    LANGUAGE("Language Student"),              // 어학 연수
    OTHERS("Others");                          // 기타

    private final String description;
}