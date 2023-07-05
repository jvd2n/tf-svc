package com.innogrid.tabcloudit.terraform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Terraform Cloud API")
                .description("[TabCloudit] Terraform Cloud API Docs")
                .version("1.0.0");

        return new OpenAPI().info(info);
    }

}
