package com.example.ingle.domain.reward;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position", nullable = false, unique = true)
    @Min(1)
    private Integer position; // 리워드 칸 위치
    //[1][2][3]
    //[4][5][6]
    //[7][8][9]

    @Column(nullable = false, length = 20)
    private String name; // 리워드 이름

    @Column(nullable = false, length = 20)
    private String title; // 리워드 카드 제목

    @Column(nullable = false, length = 100)
    private String keword; // 리워드 카드 내 키워드

    @Column(name = "reward_image_url", nullable = false)
    private String rewardImageUrl;
}
