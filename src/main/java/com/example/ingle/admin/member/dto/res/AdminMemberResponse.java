package com.example.ingle.admin.member.dto.res;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "관리자 회원 조회 응답 DTO")
public record AdminMemberResponse(

        @Schema(description = "회원 ID", example = "1")
        Long memberId,

        @Schema(description = "학번", example = "202301452")
        String studentId,

        @Schema(description = "닉네임", example = "IngleFan")
        String nickname,

        @Schema(description = "학과", example = "Dept. of Computer Science & Engineering")
        String department,

        @Schema(description = "학생 유형", example = "Exchange Student")
        String studentType,

        @Schema(description = "국가", example = "South Korea")
        String country,

        @Schema(description = "권한", example = "USER")
        Role role,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
        String imageUrl,

        @Schema(description = "가입일", example = "2024-01-01T10:00:00")
        LocalDateTime createdAt,

        @Schema(description = "밴 여부", example = "false")
        Boolean isBanned
) {
    public static AdminMemberResponse from(Member member) {
        return new AdminMemberResponse(
                member.getId(),
                member.getStudentId(),
                member.getNickname(),
                member.getDepartment().getFullName(),
                member.getStudentType().getDescription(),
                member.getCountry().getFullName(),
                member.getRole(),
                member.getImageUrl(),
                member.getCreatedAt(),
                member.getRole() == Role.BANNED
        );
    }
}