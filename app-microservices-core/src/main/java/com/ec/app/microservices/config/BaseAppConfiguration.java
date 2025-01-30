package com.ec.app.microservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

/**
 * Base configuration
 *
 * @author daleonv
 * @version 1.0
 */

@Configuration
@EntityScan(basePackages = { "com.ec.app" })
@EnableJpaRepositories(basePackages = { "com.ec.app" })
@ComponentScan(basePackages = { "com.ec.app" })
@EnableTransactionManagement
public class BaseAppConfiguration {

    @Value("${spring.profiles.active}")
    String env;

    @PostConstruct
    private void setEnvironment(){
        System.setProperty("env", env);
    }
}
