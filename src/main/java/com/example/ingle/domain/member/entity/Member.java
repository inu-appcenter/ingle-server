package com.example.ingle.domain.member.entity;

import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.domain.member.enums.StudentType;
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
    private StudentType studentType;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(String studentId, Department department, StudentType studentType, String nickname, Role role, String imageUrl) {
        this.studentId = studentId;
        this.department = department;
        this.studentType = studentType;
        this.nickname = nickname;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    public static Member fromSignupRequest(MemberInfoRequest memberInfoRequest) {
        return new Member(
                memberInfoRequest.getStudentId(),
                memberInfoRequest.getDepartment(),
                memberInfoRequest.getStudentType(),
                memberInfoRequest.getNickname(),
                Role.USER,
                "https://example.com/default-profile.jpg"
        );
    }

    public void updateMember(@Valid MemberInfoRequest memberInfoRequest) {
        this.studentId = memberInfoRequest.getStudentId() != null ? memberInfoRequest.getStudentId() : this.studentId;
        this.department = memberInfoRequest.getDepartment() != null ? memberInfoRequest.getDepartment() : this.department;
        this.nickname = memberInfoRequest.getNickname() != null ? memberInfoRequest.getNickname() : this.nickname;
        this.studentType = memberInfoRequest.getStudentType() != null ? memberInfoRequest.getStudentType() : this.studentType;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}