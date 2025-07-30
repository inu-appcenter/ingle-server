package com.example.ingle.domain.map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MapRepository extends JpaRepository<Map,Long> {

    @Query("""
        SELECT m FROM Map m
        WHERE m.latitude BETWEEN :minLat AND :maxLat
        AND m.longitude BETWEEN :minLng AND :maxLng
        AND (:category IS NULL OR m.category = :category)
    """)
    List<Map> findMapsInBounds(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng,
            @Param("category") MapCategory category);

    List<Map> findByNameContainingOrderByCreatedAtDesc(String name);
}
