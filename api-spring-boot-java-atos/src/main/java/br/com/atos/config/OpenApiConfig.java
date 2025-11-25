package br.com.atos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST do zero com Java Kubernets e Docker")
                        .version("V1")
                        .description("API REST do zero com Java Kubernets e Docker")
                        .termsOfService("https://github.com/AvohaiTeixeira/api-spring-boot-java-atos")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/AvohaiTeixeira/api-spring-boot-java-atos")
                        )
                );
    }
}
