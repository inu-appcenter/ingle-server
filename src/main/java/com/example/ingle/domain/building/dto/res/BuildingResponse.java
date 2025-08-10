package com.example.ingle.domain.building.dto.res;

import com.example.ingle.domain.building.entity.Building;
import com.example.ingle.domain.building.enums.BuildingCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "건물 정보 응답 DTO")
public class BuildingResponse {

    @Schema(description = "건물 Id", example = "1")
    private Long buildingId;

    @Schema(description = "건물 이름", example = "대학본부")
    private String buildingName;

    @Schema(description = "위도", example = "37.376833680573")
    private Double latitude;

    @Schema(description = "경도", example = "126.6347435192162")
    private Double longitude;

    @Schema(description = "지도 카테고리", example = "SCHOOL_BUILDING")
    private BuildingCategory buildingCategory;

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