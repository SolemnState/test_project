package com.innotech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JDBCConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dmds = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/example");
        dmds.setDriverClassName("org.postgresql.Driver");
        dmds.setSchema("test");
        dmds.setUsername("user");
        dmds.setPassword("password");
        return dmds;
    }
}
