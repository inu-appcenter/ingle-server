package com.example.ingle.global.jwt.refreshToken;

import com.example.ingle.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, length = 512)
    private String refreshToken;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public RefreshToken updateValue(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Builder
    private RefreshToken(Member member, String refreshToken) {
        this.memberId = member.getId();
        this.studentId = member.getStudentId();
        this.refreshToken = refreshToken;
    }
}