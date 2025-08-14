package com.example.ingle.domain.building.repository;

import com.example.ingle.domain.building.domain.ClosedDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClosedDayRepository extends JpaRepository<ClosedDay, Long> {

    List<ClosedDay> findByBuildingId(Long buildingId);
}
