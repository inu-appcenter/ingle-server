package com.example.ingle.domain.building.repository;

import com.example.ingle.domain.building.entity.Building;
import com.example.ingle.domain.building.enums.BuildingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,Long> {

    @Query("""
        SELECT m FROM Building m
        WHERE m.latitude BETWEEN :minLat AND :maxLat
        AND m.longitude BETWEEN :minLng AND :maxLng
        AND (:category IS NULL OR m.category = :category)
    """)
    List<Building> findMapsInBounds(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng,
            @Param("category") BuildingCategory category);

    List<Building> findByBuildingNameContainingOrderByCreatedAtDesc(String name);
}
