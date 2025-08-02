package com.example.ingle.domain.map.dto.res;

import com.example.ingle.domain.map.Map;
import com.example.ingle.domain.map.enums.MapCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "지도 정보 응답 DTO")
public class MapResponseDto {

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

    @Builder
    private MapResponseDto(Map map) {
        this.mapId = map.getId();
        this.name = map.getName();
        this.latitude = map.getLatitude();
        this.longitude = map.getLongitude();
        this.category = map.getCategory();
    }
}
