package com.example.ingle.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class MysqlDataSourceProperties { // application.yml 에 정의한 MySQL 데이터베이스 접속 정보를 객체로 매핑하기 위한 전용 설정 클래스

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}