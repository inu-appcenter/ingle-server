package com.example.ingle.admin.member.dto.req;

import com.example.ingle.domain.member.enums.Country;
import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.domain.member.enums.StudentType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관리자 회원 검색 요청 DTO")
public record AdminMemberSearchRequest (
    String studentId,
    String nickname,
    Department department,
    Role role,
    StudentType studentType,
    Country country
){
}

