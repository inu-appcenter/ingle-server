package com.example.ingle.domain.tutorial;

import com.example.ingle.domain.tutorial.dto.res.TutorialResponseDto;
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

    @Transactional(readOnly = true)
    public List<TutorialResponseDto> getTutorialsByCategory(Category category) {

        log.info("[카테고리별 튜토리얼 조회] category: {}", category);

        List<Tutorial> tutorials = tutorialRepository.findByCategoryOrderByRewardPosition(category);

        List<TutorialResponseDto> tutorialResponseDtos = tutorials.stream()
                .map(tutorial -> TutorialResponseDto.builder()
                        .tutorial(tutorial)
                        .build())
                .toList();

        log.info("[카테고리별 튜토리얼 조회 성공]");

        return tutorialResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<TutorialResponseDto> getAllTutorials() {

        log.info("[전체 튜토리얼 조회]");

        List<Tutorial> tutorials = tutorialRepository.findAllByOrderByRewardPosition();

        List<TutorialResponseDto> tutorialResponseDtos = tutorials.stream()
                .map(tutorial -> TutorialResponseDto.builder()
                        .tutorial(tutorial)
                        .build())
                .toList();

        log.info("[전체 튜토리얼 조회 성공]");

        return tutorialResponseDtos;
    }
}