package com.example.ingle.domain.stamp;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stamp")
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name; // 스탬프 이름

    @Column(nullable = false, length = 20)
    private String cardtitle; // 카드 제목

    @Column(nullable = false, length = 100)
    private String keword; // 카드 내 키워드

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
