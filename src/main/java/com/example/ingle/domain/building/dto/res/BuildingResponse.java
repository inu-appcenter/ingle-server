package com.example.ingle.domain.building.dto.res;

import com.example.ingle.domain.building.domain.Building;
import com.example.ingle.domain.building.enums.BuildingCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "건물 정보 응답 DTO")
public record BuildingResponse(

        @Schema(description = "건물 Id", example = "1")
        Long buildingId,

        @Schema(description = "건물 이름", example = "대학본부")
        String buildingName,

        @Schema(description = "위도", example = "37.376833680573")
        Double latitude,

        @Schema(description = "경도", example = "126.6347435192162")
        Double longitude,

        @Schema(description = "지도 카테고리", example = "SCHOOL_BUILDING")
        BuildingCategory buildingCategory

) {
    public static BuildingResponse from(Building building) {
        return new BuildingResponse(
                building.getId(),
                building.getBuildingName(),
                building.getLatitude(),
                building.getLongitude(),
                building.getBuildingCategory()
        );
    }
}