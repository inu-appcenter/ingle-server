package com.example.ingle.domain.member.service;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.member.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDetail loadUserByUsername(String studentId) throws UsernameNotFoundException {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException(studentId));

        return new MemberDetail(member);
    }
}