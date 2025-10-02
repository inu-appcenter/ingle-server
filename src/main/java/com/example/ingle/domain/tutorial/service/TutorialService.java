package com.example.ingle.domain.tutorial.service;

import com.example.ingle.domain.tutorial.repository.TutorialRepository;
import com.example.ingle.domain.tutorial.dto.res.TutorialResponse;
import com.example.ingle.domain.tutorial.entity.Category;
import com.example.ingle.domain.tutorial.entity.Tutorial;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepository tutorialRepository;

    /**
     * 카테고리별 튜토리얼 목록 조회
     * @param category
     * @return
     */
    @Transactional(readOnly = true)
    public List<TutorialResponse> getTutorialsByCategory(Category category) {

        List<Tutorial> tutorials = tutorialRepository.findByCategoryOrderById(category);

        return tutorials.stream()
                .map(TutorialResponse::from)
                .toList();
    }

    /**
     * 전체 튜토리얼 목록 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<TutorialResponse> getAllTutorials() {

        List<Tutorial> tutorials = tutorialRepository.findAllByOrderById();

        return tutorials.stream()
                .map(TutorialResponse::from)
                .toList();
    }
}