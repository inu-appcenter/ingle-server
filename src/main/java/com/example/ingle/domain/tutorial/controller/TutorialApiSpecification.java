package com.example.ingle.domain.tutorial.controller;

import com.example.ingle.domain.tutorial.entity.Category;
import com.example.ingle.domain.tutorial.dto.res.TutorialResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Tutorial", description = "튜토리얼 관련 API")
public interface TutorialApiSpecification {

    @Operation(
            summary = "전체 튜토리얼 목록 조회",
            description = "모든 튜토리얼을 리워드 위치 순으로 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "튜토리얼 목록 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TutorialResponse.class)),
                                    examples = @ExampleObject(
                                            name = "전체 튜토리얼 목록 응답 예시",
                                            value = """
                                                [
                                                  {
                                                    "id": 1,
                                                    "title": "Transportation",
                                                    "category": "CAMPUS_LIFE"
                                                  },
                                                  {
                                                    "id": 2,
                                                    "title": "Tuition Payments",
                                                    "category": "ACADEMIC_AFFAIRS"
                                                  },
                                                  {
                                                    "id": 3,
                                                    "title": "Hospital",
                                                    "category": "LIFE_STYLE"
                                                  }
                                                ]
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<TutorialResponse>> getAllTutorials();

    @Operation(
            summary = "카테고리별 튜토리얼 목록 조회",
            description = "특정 카테고리의 튜토리얼을 리워드 위치 순으로 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리별 튜토리얼 목록 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TutorialResponse.class)),
                                    examples = @ExampleObject(
                                            name = "BASIC 카테고리 튜토리얼 목록 응답 예시",
                                            value = """
                                                [
                                                  {
                                                    "id": 1,
                                                    "title": "Transportation",
                                                    "category": "CAMPUS_LIFE"
                                                  },
                                                  {
                                                    "id": 2,
                                                    "title": "Tuition Payments",
                                                    "category": "ACADEMIC_AFFAIRS"
                                                  },
                                                  {
                                                    "id": 4,
                                                    "title": "Course Registration",
                                                    "category": "ACADEMIC_AFFAIRS"
                                                  }
                                                ]
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<TutorialResponse>> getTutorialsByCategory(@RequestParam Category category);
}