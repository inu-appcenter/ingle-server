package com.example.ingle.domain.stamp.repository;

import com.example.ingle.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    Optional<Stamp> findByTutorialId(Long tutorialId);
    List<Stamp> findAllByOrderById();
}
