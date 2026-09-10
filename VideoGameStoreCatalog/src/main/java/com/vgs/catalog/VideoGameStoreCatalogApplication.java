package com.vgs.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VideoGameStoreCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoGameStoreCatalogApplication.class, args);
    }
}