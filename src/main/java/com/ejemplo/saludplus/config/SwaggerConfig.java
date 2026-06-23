package com.ejemplo.saludplus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("API SaludPlus – Hospital Vida & Meditación")
                                                .version("1.0")
                                                .description("Documentación de la API para el sistema de gestión hospitalaria SaludPlus. "
                                                                + "Incluye gestión de pacientes, médicos, atenciones, fichas clínicas, "
                                                                + "reportes personalizados y administración de base de datos."))
                                .addSecurityItem(new SecurityRequirement().addList("Bearer JWT"))
                                .components(new Components()
                                                .addSecuritySchemes("Bearer JWT",
                                                                new SecurityScheme()
                                                                                .name("Authorization")
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")
                                                                                .description("Ingresa tu token JWT. Ejemplo: eyJhbGciOi...")));
        }
}
