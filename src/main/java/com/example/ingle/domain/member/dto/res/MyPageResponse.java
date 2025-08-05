package com.example.ingle.domain.member.dto.res;

import com.example.ingle.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "마이페이지 정보 응답 DTO")
public record MyPageResponse(

        @Schema(description = "회원 Id", example = "1")
        Long memberId,

        @Schema(description = "학번", example = "202301452")
        String studentId,

        @Schema(description = "학과", example = "COMPUTER_ENG")
        String department,

        @Schema(description = "파견 유형", example = "EXCHANGE")
        String program,

        @Schema(description = "닉네임", example = "IngleFan")
        String nickname,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String imageUrl

) {
    public static MyPageResponse from(Member member) {
        return new MyPageResponse(
                member.getId(),
                member.getStudentId(),
                member.getDepartment().getFullName(),
                member.getStudentType().getDescription(),
                member.getNickname(),
                member.getImageUrl()
        );
    }
}
