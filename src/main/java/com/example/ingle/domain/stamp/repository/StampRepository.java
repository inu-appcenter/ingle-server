package com.example.ingle.domain.stamp.repository;

import com.example.ingle.domain.member.dto.res.MemberProfileImageResponse;
import com.example.ingle.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    Optional<Stamp> findByTutorialId(Long tutorialId);

    List<Stamp> findAllByOrderById();
}
