package com.example.ingle.domain.stamp.res;

import com.example.ingle.domain.stamp.Stamp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "스탬프 상세 정보 응답 DTO")
public class StampResponse {

    @Schema(description = "스탬프 ID", example = "1")
    private final Long id;

    @Schema(description = "멤버 ID", example = "1")
    private final Long memberId;

    @Schema(description = "해당 튜토리얼 ID", example = "1")
    private final Long tutorialId;

    @Schema(description = "스탬프 이름", example = "Transit")
    private final String name;

    @Schema(description = "스탬프 카드 제목", example = "Transportation")
    private final String cardTitle;

    @Schema(description = "스탬프 키워드", example = "Airport, Subway, Buses, Bike")
    private final String keyword;

    @Schema(description = "리워드 이미지", example = "https://ingle-server.inuappcenter.kr/images/reward_1.png")
    private final String imageUrl;

    @Schema(description = "완료 시간", example = "2025-01-15T14:30:00")
    private final LocalDateTime completedAt;

    @Builder
    public StampResponse(Stamp stamp) {
        this.id = stamp.getId();
        this.memberId = stamp.getMemberId();
        this.tutorialId = stamp.getTutorialId();
        this.name = stamp.getName();
        this.cardTitle = stamp.getCardTitle();
        this.keyword = stamp.getKeyword();
        this.imageUrl = stamp.getImageUrl();
        this.completedAt = stamp.getCompletedAt();
    }

}
