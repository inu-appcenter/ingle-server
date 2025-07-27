package com.example.ingle.global.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
@EnableTransactionManagement // 트랜잭션 처리를 위해 @Transactional 어노테이션이 작동하도록 설정
@EnableJpaRepositories( // JpaRepository를 사용하는 클래스들의 위치 지정
        basePackages = "com.example.ingle",
        entityManagerFactoryRef = "mysqlEntityManagerFactory", // mysqlEntityManagerFactory와 mysqlTransactionManager를 사용
        transactionManagerRef = "mysqlTransactionManager"
)
@EntityScan(basePackages = "com.example.ingle")
public class MysqlConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public MysqlDataSourceProperties mysqlDataSourceProperties() {
        return new MysqlDataSourceProperties();
    }

    @Bean(name = "mysqlDataSource")
    @Primary
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
                .packages("com.example.ingle")
                .persistenceUnit("mysql")
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
