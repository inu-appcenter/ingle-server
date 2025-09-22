package com.example.ingle.domain.building.domain;

import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "building")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Building extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "building_name", nullable = false, length = 200)
    private String buildingName;

    @Column(name = "building_images")
    @ElementCollection
    private List<String> buildingImages;

    @Column(name = "building_category", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private BuildingCategory buildingCategory;

    @Column(name = "building_number")
    private Integer buildingNumber;

    @Column(name = "building_code", length = 10)
    private String buildingCode;

    @Column(length = 100)
    private String location;

    private Integer floor;

    private String address;

    @Column(name = "open_time", length = 10)
    private String openTime;

    @Column(name = "close_time", length = 10)
    private String closeTime;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "ingle_pick")
    private String inglePick;

    @Column(name = "building_url")
    private String buildingUrl;
}
