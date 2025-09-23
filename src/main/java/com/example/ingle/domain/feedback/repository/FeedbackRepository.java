package com.example.ingle.domain.feedback.repository;

import com.example.ingle.domain.feedback.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
