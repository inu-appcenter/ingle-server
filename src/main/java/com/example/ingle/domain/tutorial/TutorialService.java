package com.example.ingle.domain.tutorial;

import com.example.ingle.domain.tutorial.dto.res.TutorialResponse;
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
    public List<TutorialResponse> getTutorialsByCategory(Category category) {

        List<Tutorial> tutorials = tutorialRepository.findByCategoryOrderByRewardPosition(category);

        List<TutorialResponse> tutorialResponses = tutorials.stream()
                .map(tutorial -> TutorialResponse.builder()
                        .tutorial(tutorial)
                        .build())
                .toList();

        return tutorialResponses;
    }

    @Transactional(readOnly = true)
    public List<TutorialResponse> getAllTutorials() {

        List<Tutorial> tutorials = tutorialRepository.findAllByOrderByRewardPosition();

        List<TutorialResponse> tutorialResponses = tutorials.stream()
                .map(tutorial -> TutorialResponse.builder()
                        .tutorial(tutorial)
                        .build())
                .toList();

        return tutorialResponses;
    }
}