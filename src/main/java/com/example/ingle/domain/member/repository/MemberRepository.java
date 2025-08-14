package com.example.ingle.domain.member.repository;

import com.example.ingle.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByStudentId(String studentId);
    boolean existsByNickname(String nickname);
    boolean existsByStudentId(String studentId);
}
