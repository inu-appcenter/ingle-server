package com.example.ingle.admin.statistics.dto;

/*
*  JPQL new 생성자에서 사용하는 중간 DTO (Repository용)
*/
public record AdminStampProgress(
        String stampName,
        Long acquiredCount
) {
}