package com.example.ingle.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류가 발생했습니다."),
    MISSING_PART(HttpStatus.BAD_REQUEST, 400, "요청에 필요한 부분이 없습니다."),
    NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, 404, "요청하신 API가 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "지원하지 않는 HTTP 메서드입니다."),

    // Jwt
    JWT_NOT_VALID(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 유효하지 않은 Jwt"),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 419, "[Jwt] 만료된 엑세스 토큰입니다."),
    JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 420, "[Jwt] 만료된 리프레시 토큰입니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 잘못된 토큰 형식입니다."),
    JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 유효하지 않은 서명입니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 지원하지 않는 토큰입니다."),
    JWT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "[Jwt] 리프레시 토큰 조회 실패"),
    JWT_NOT_MATCH(HttpStatus.BAD_REQUEST, 400, "[Jwt] 리프레시 토큰 불일치"),
    JWT_ENTRY_POINT(HttpStatus.UNAUTHORIZED, 401, "[Jwt] 인증되지 않은 사용자입니다."),
    JWT_ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, "[Jwt] 리소스에 접근할 권한이 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "회원을 찾을 수 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, 401, "로그인이 실패하였습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 400, "이미 존재하는 회원입니다."),

    // Building
    BUILDING_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "건물을 찾을 수 없습니다."),

    // Validation
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, 400, "요청한 값이 올바르지 않습니다."),

    // Tutorial
    TUTORIAL_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "튜토리얼을 찾을 수 없습니다."),
    TUTORIAL_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, 400, "이미 완료한 튜토리얼입니다."),

    // StampIMAGE_NOT_FOUND
    STAMP_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "스탬프를 찾을 수 없습니다."),

    // Image
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "이미지를 찾을 수 없습니다."),
    IMAGE_CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "이미지 변환에 실패했습니다."),
    INVALID_FILE_PATH(HttpStatus.BAD_REQUEST, 400, "잘못된 파일 경로 접근 시도입니다."),
    ;

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
