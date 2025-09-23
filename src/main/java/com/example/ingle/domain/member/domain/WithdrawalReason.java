package com.example.ingle.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "withdrawal_reason")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawalReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 50)
    private String content;

    private WithdrawalReason(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

    public static WithdrawalReason of(Long memberId, String content) {
        return new WithdrawalReason(memberId, content);
    }
}
