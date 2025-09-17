package com.example.ingle.domain.building.repository;

import com.example.ingle.domain.building.domain.Building;
import com.example.ingle.domain.building.dto.res.BuildingResponse;
import com.example.ingle.domain.building.enums.BuildingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,Long> {

    @Query("""
        SELECT new com.example.ingle.domain.building.dto.res.BuildingResponse
            (m.id, m.buildingName, m.latitude, m.longitude, m.buildingCategory)
        FROM Building m
        WHERE m.latitude BETWEEN :minLat AND :maxLat
        AND m.longitude BETWEEN :minLng AND :maxLng
        AND (:category IS NULL OR m.buildingCategory = :category)
    """)
    List<BuildingResponse> findBuildingsInBounds(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng,
            @Param("category") BuildingCategory category);

    @Query("""
        SELECT new com.example.ingle.domain.building.dto.res.BuildingResponse
            (m.id, m.buildingName, m.latitude, m.longitude, m.buildingCategory)
        FROM Building m
        WHERE m.buildingName LIKE CONCAT('%', :buildingName, '%')
        ORDER BY m.createdAt DESC
    """)
    List<BuildingResponse> findByBuildingNameContainingOrderByCreatedAtDesc(String buildingName);
}
