package com.example.ingle.domain.member;

import com.example.ingle.domain.member.dto.req.SignupRequestDto;
import com.example.ingle.domain.member.dto.req.UpdateMemberRequestDto;
import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.Program;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Program program;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(name = "terms_agreed", nullable = false)
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

    public void updateMember(@Valid UpdateMemberRequestDto updateMemberRequestDto) {
        this.studentId = updateMemberRequestDto.getStudentId() != null ? updateMemberRequestDto.getStudentId() : this.studentId;
        this.department = updateMemberRequestDto.getDepartment() != null ? updateMemberRequestDto.getDepartment() : this.department;
        this.nickname = updateMemberRequestDto.getNickname() != null ? updateMemberRequestDto.getNickname() : this.nickname;
        this.program = updateMemberRequestDto.getProgram() != null ? updateMemberRequestDto.getProgram() : this.program;
    }
}