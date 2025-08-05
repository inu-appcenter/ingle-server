package com.example.ingle.domain.building.entity;

import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.domain.building.enums.ClosedDay;
import com.example.ingle.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
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

    @Column(name = "building_category", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private BuildingCategory buildingCategory;

    @Column(name = "building_number")
    private Integer buildingNumber;

    @Column(name = "building_code", length = 10)
    private String buildingCode;

    @Column(length = 30)
    private String location;

    private Integer floor;

    @Column(name = "open_time", length = 10)
    private String openTime;

    @Column(name = "close_time", length = 10)
    private String closeTime;

    @Column(name = "closed_day")
    @ElementCollection(targetClass = ClosedDay.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "map_closed_days", joinColumns = @JoinColumn(name = "map_id"))
    private Set<ClosedDay> closedDays = new HashSet<>();

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;
}
