package com.example.ingle.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Program {
    EXCHANGE("Exchange Student"),        // 교환학생
    UNDERGRADUATE("Undergraduate Student"), // 학부생
    GRADUATE("Graduate Student"),        // 대학원생
    OTHER("Other");                      // 기타

    private final String description;
}