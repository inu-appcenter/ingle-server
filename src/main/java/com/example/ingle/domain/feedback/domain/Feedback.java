package com.example.ingle.domain.feedback.domain;

import com.example.ingle.domain.feedback.dto.req.FeedbackRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 1000)
    private String content;

    private Feedback(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

    public static Feedback of(Long memberId, String content) {
        return new Feedback(memberId, content);
    }
}
