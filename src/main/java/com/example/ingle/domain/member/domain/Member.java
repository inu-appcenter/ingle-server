package com.example.ingle.domain.member.domain;

import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.enums.Country;
import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.domain.member.enums.StudentType;
import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, length = 70)
    @Enumerated(EnumType.STRING)
    private Department department;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private StudentType studentType;

    @Column(nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(name = "image_url", length = 2000)
    private String imageUrl;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String studentId, Department department, StudentType studentType, Country country , String nickname, Role role, String imageUrl) {
        this.studentId = studentId;
        this.department = department;
        this.studentType = studentType;
        this.country = country;
        this.nickname = nickname;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    public static Member fromSignupRequest(MemberInfoRequest memberInfoRequest) {
        return new Member(
                memberInfoRequest.studentId(),
                memberInfoRequest.department(),
                memberInfoRequest.studentType(),
                memberInfoRequest.country(),
                memberInfoRequest.nickname(),
                Role.USER,
                null
        );
    }

    public void updateMember(MemberInfoRequest memberInfoRequest) {
        this.studentId = memberInfoRequest.studentId() != null ? memberInfoRequest.studentId() : this.studentId;
        this.department = memberInfoRequest.department() != null ? memberInfoRequest.department() : this.department;
        this.country = memberInfoRequest.country() != null ? memberInfoRequest.country() : this.country;
        this.studentType = memberInfoRequest.studentType() != null ? memberInfoRequest.studentType() : this.studentType;
        this.nickname = memberInfoRequest.nickname() != null ? memberInfoRequest.nickname() : this.nickname;
    }

    public void banMember() {
        this.role = Role.BANNED;
    }

    public void unbanMember() {
        this.role = Role.USER;
    }

    public void updateProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}