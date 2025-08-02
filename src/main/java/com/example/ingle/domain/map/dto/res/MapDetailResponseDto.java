package com.example.ingle.domain.map.dto.res;

import com.example.ingle.domain.map.Map;
import com.example.ingle.domain.map.enums.ClosedDay;
import com.example.ingle.domain.map.enums.MapCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Schema(description = "지도 상세 정보 응답 DTO")
public class MapDetailResponseDto {

    @Schema(description = "지도 Id", example = "1")
    private final Long mapId;

    @Schema(description = "지도 이름", example = "대학본부")
    private final String name;

    @Schema(description = "위도", example = "37.376833680573")
    private final Double latitude;

    @Schema(description = "경도", example = "126.6347435192162")
    private final Double longitude;

    @Schema(description = "지도 카테고리", example = "SCHOOL_BUILDING")
    private final MapCategory category;

    @Schema(description = "호관", example = "1")
    private final Integer buildingNumber;

    @Schema(description = "건물 코드", example = "SH")
    private final String buildingCode;

    @Schema(description = "위치", example = "복지회관")
    private final String location;

    @Schema(description = "층수", example = "1")
    private final Integer floor;

    @Schema(description = "오픈 시간", example = "00:00")
    private final String openTime;

    @Schema(description = "클로즈 시간", example = "24:00")
    private final String closeTime;

    @Schema(description = "휴무일 목록", example = "[\"MONDAY\", \"SUNDAY\"]")
    private final Set<ClosedDay> closedDays;

    @Schema(description = "전화번호", example = "032-1234-5678")
    private final String phoneNumber;

    @Builder
    private MapDetailResponseDto(Map map) {
        this.mapId = map.getId();
        this.name = map.getName();
        this.latitude = map.getLatitude();
        this.longitude = map.getLongitude();
        this.category = map.getCategory();
        this.buildingNumber = map.getBuildingNumber();
        this.buildingCode = map.getBuildingCode();
        this.location = map.getLocation();
        this.floor = map.getFloor();
        this.openTime = map.getOpenTime();
        this.closeTime = map.getCloseTime();
        this.closedDays = map.getClosedDays();
        this.phoneNumber = map.getPhoneNumber();
    }
}
