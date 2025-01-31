package com.ec.app.microservices;

import com.ec.app.microservices.config.BaseAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Test procedures Application
 *
 * @author daleonv
 * @version 1.0
 */

@EnableDiscoveryClient
@Import({BaseAppConfiguration.class})
@SpringBootApplication(scanBasePackages = {"com.ec.app"})
@ComponentScan(basePackages = {"com.ec.app"})
public class ProcedureApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ProcedureApplication.class);
    }

}
