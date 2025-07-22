package com.example.ingle.domain.member;

import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String department;

    @Column(nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(length = 100)
    private String profileImage;

    @Column(nullable = false)
    private boolean terms;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    private Member(String department, String studentId, String nickname, String profileImage, boolean terms, Role role) {
        this.department = department;
        this.studentId = studentId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.terms = terms;
        this.role = role;
    }

    // 학과 업데이트
    public void updateDepartment(String department) {
        this.department = department;
    }

    // 학번 업데이트
    public void updateStudentId(String studentId) {
        this.studentId = studentId;
    }

    // 닉네임 업데이트
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    // 프로필 사진 업데이트
    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}