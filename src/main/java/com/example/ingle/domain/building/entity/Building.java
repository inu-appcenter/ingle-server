package com.example.ingle.domain.building.entity;

import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "building")
public class Building extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BuildingCategory category;

    @Column(name = "building_number", nullable = true)
    private Integer buildingNumber;

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
