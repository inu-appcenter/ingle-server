package com.example.ingle.global.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement // 트랜잭션 처리를 위해 @Transactional 어노테이션이 작동하도록 설정
@EnableJpaRepositories( // JpaRepository를 사용하는 클래스들의 위치 지정
        basePackages = "com.example.ingle", // 지정한 패키지 하위의 JpaRepository 인터페이스를 스캔
        entityManagerFactoryRef = "mysqlEntityManagerFactory", // mysqlEntityManagerFactory와 mysqlTransactionManager를 사용
        transactionManagerRef = "mysqlTransactionManager" // 해당 Repository들이 사용할 TransactionManager를 MySQL 전용으로 지정
)
@EntityScan(basePackages = "com.example.ingle") // @Entity로 정의된 JPA 엔티티 클래스들을 이 패키지에서 탐색
public class MysqlConfig {

    private final JpaProperties jpaProperties;  // JpaProperties는 spring.jpa로 시작하는 모든 설정을 자동으로 매핑해주는 클래스

    @Bean
    public MysqlDataSourceProperties mysqlDataSourceProperties() { // spring.datasource.*에 정의된 URL, username, password 등을 바인딩
        return new MysqlDataSourceProperties();
    }

    @Bean(name = "mysqlDataSource")
    @Primary // 여러 DataSource Bean이 있을 때 기본으로 사용될 Bean 지정
    public DataSource mysqlDataSource(MysqlDataSourceProperties props) {
        return DataSourceBuilder.create()
                .url(props.getUrl())
                .username(props.getUsername())
                .password(props.getPassword())
                .driverClassName(props.getDriverClassName())
                .type(HikariDataSource.class)
                .build();
    }

    // JPA가 엔티티를 관리하기 위한 팩토리
    // MySQL DataSource를 사용
    // 아래 패키지의 @Entity 클래스를 관리 대상에 포함
    @Bean(name = "mysqlEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mysqlDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.ingle") // @Entity 관리 대상 패키지
                .persistenceUnit("mysql") // persistence 단위 이름
                .properties(jpaProperties.getProperties())
                .build();
    }

    // JPA에서 사용하는 트랜잭션 매니저
    // @Transactional 동작 시 이 트랜잭션 매니저를 기본으로 사용
    @Bean(name = "mysqlTransactionManager")
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}