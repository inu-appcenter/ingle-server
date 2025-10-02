package com.example.ingle.domain.tutorial.controller;

import com.example.ingle.domain.tutorial.Category;
import com.example.ingle.domain.tutorial.TutorialService;
import com.example.ingle.domain.tutorial.dto.res.TutorialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tutorials")
public class TutorialController implements TutorialApiSpecification {

    private final TutorialService tutorialService;

    /**
     * 전체 튜토리얼 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<List<TutorialResponse>> getAllTutorials() {
        return ResponseEntity.status(HttpStatus.OK).body(tutorialService.getAllTutorials());
    }

    /**
     * 카테고리별 조회
     * @param category
     * @return
     */
    @GetMapping("/category")
    public ResponseEntity<List<TutorialResponse>> getTutorialsByCategory(@RequestParam Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(tutorialService.getTutorialsByCategory(category));
    }

}