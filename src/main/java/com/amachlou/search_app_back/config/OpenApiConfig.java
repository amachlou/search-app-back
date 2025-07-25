package com.amachlou.search_app_back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 @Configuration
public class OpenApiConfig {

     @Bean
     public OpenAPI productOpenAPI() {
         return new OpenAPI()
                 .info(new Info()
                         .title("Product Search API")
                         .description("API for managing and searching products with Elasticsearch")
                         .version("v1.0"));
     }

     @Bean
     public GroupedOpenApi publicApi() {
         return GroupedOpenApi.builder()
                 .group("products")
                 .pathsToMatch("/api/**")
                 .build();
     }

}
