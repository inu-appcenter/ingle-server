package com.example.ingle.domain.member;

import com.example.ingle.domain.member.dto.req.SignupRequestDto;
import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.Program;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Program program;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(nullable = false)
    private boolean termsAgreed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(SignupRequestDto signupRequestDto) {
        this.studentId = signupRequestDto.getStudentId();
        this.nickname = signupRequestDto.getNickname();
        this.department = signupRequestDto.getDepartment();
        this.program = signupRequestDto.getProgram();
        this.termsAgreed = signupRequestDto.isTermsAgreed();
        this.role = Role.USER;
    }

    // 학과 업데이트
    public void updateDepartment(Department department) {
        this.department = department;
    }

    // 파견 유형 업데이트
    public void updateStudentId(Program program) {
        this.program = program;
    }

    // 닉네임 업데이트
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}