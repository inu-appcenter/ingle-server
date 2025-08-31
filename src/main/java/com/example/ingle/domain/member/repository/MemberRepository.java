package com.example.ingle.domain.member.repository;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByStudentId(String studentId);
    boolean existsByNickname(String nickname);
    boolean existsByStudentId(String studentId);

    long countByRole(Role role);
}
