package com.example.ingle.domain.map;

import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "maps")
public class Map extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MapCategory category;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(name = "building_code", nullable = false)
    private String buildingCode;

    @Column(nullable = false)
    private Integer floor;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = true)
    private String image;

    @Column(name = "open_time", nullable = false)
    private String openTime;

    @Column(name = "close_time", nullable = false)
    private String closeTime;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
}
