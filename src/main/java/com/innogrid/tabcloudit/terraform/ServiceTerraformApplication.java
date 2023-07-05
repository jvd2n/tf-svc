package com.innogrid.tabcloudit.terraform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.innogrid.tabcloudit.terraform")
public class ServiceTerraformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTerraformApplication.class, args);
    }

}
