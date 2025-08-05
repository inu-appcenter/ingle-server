package com.example.ingle.domain.building.entity;

import com.example.ingle.domain.building.enums.ClosedDayEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "closed_day")
public class ClosedDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "closed_day", nullable = false, length = 20)
    private ClosedDayEnum closedDay;

    @Column(name = "building_id", nullable = false)
    private Long buildingId;
}
