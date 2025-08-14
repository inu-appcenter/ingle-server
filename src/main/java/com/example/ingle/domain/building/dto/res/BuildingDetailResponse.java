package com.example.ingle.domain.building.dto.res;

import com.example.ingle.domain.building.domain.Building;
import com.example.ingle.domain.building.domain.ClosedDay;
import com.example.ingle.domain.building.enums.BuildingCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "건물 상세 정보 응답 DTO")
public record BuildingDetailResponse(

        @Schema(description = "건물 Id", example = "1")
        Long buildingId,

        @Schema(description = "건물 이름", example = "대학본부")
        String buildingName,

        @Schema(description = "위도", example = "37.376833680573")
        Double latitude,

        @Schema(description = "경도", example = "126.6347435192162")
        Double longitude,

        @Schema(description = "건물 카테고리", example = "SCHOOL_BUILDING")
        BuildingCategory buildingCategory,

        @Schema(description = "호관", example = "1")
        Integer buildingNumber,

        @Schema(description = "건물 코드", example = "SH")
        String buildingCode,

        @Schema(description = "위치", example = "복지회관")
        String location,

        @Schema(description = "층수", example = "1")
        Integer floor,

        @Schema(description = "오픈 시간", example = "00:00")
        String openTime,

        @Schema(description = "클로즈 시간", example = "24:00")
        String closeTime,

        @Schema(description = "전화번호", example = "032-1234-5678")
        String phoneNumber,

        @Schema(description = "휴무일", example = "No Closed Days / Monday")
        List<String> closedDays

) {
    public static BuildingDetailResponse from(Building building, List<ClosedDay> closedDays) {
        return new BuildingDetailResponse(
                building.getId(),
                building.getBuildingName(),
                building.getLatitude(),
                building.getLongitude(),
                building.getBuildingCategory(),
                building.getBuildingNumber(),
                building.getBuildingCode(),
                building.getLocation(),
                building.getFloor(),
                building.getOpenTime(),
                building.getCloseTime(),
                building.getPhoneNumber(),
                closedDays.stream().map(cd -> cd.getClosedDay().getFullName()).toList()
        );
    }
}