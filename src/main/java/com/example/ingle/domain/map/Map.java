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

    @Column(name = "building_name", nullable = true)
    private Integer buildingName;

    @Column(name = "building_code", nullable = true)
    private String buildingCode;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private Integer floor;

    @Column(name = "open_time", nullable = true)
    private String openTime;

    @Column(name = "close_time", nullable = true)
    private String closeTime;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;
}
