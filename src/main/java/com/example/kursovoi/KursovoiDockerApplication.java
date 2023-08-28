package com.example.kursovoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;



@SpringBootApplication
@EnableTransactionManagement
public class KursovoiDockerApplication {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("1000MB"));
        factory.setMaxRequestSize(DataSize.parse("1000MB"));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(KursovoiDockerApplication.class, args);
    }

}
