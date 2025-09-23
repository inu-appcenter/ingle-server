package com.example.ingle.domain.member.repository;

import com.example.ingle.domain.member.domain.WithdrawalReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalReasonRepository extends JpaRepository<WithdrawalReason, Long> {
}
