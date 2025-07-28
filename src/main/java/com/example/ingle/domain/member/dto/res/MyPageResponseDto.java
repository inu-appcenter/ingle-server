package com.example.ingle.domain.member.dto.res;

import com.example.ingle.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "마이페이지 정보 응답 DTO")
public class MyPageResponseDto {

    @Schema(description = "회원 Id", example = "1")
    private final Long memberId;

    @Schema(description = "학번", example = "202301452")
    private final String studentId;

    @Schema(description = "학과", example = "COMPUTER_ENG")
    private final String department;

    @Schema(description = "파견 유형", example = "EXCHANGE")
    private final String program;

    @Schema(description = "닉네임", example = "IngleFan")
    private final String nickname;

    @Builder
    private MyPageResponseDto(Member member) {
        this.memberId = member.getId();
        this.studentId = member.getStudentId();
        this.department = member.getDepartment().name();
        this.program = member.getProgram().name();
        this.nickname = member.getNickname();
    }
}
