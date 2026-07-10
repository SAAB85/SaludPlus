package com.saludplus.notificaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SaludPlus - Microservicio de " + "Notificaciones")
                        .version("1.0.0")
                        .description("API REST para la gestión clínica del módulo de " + "notificacioness.")
                        .contact(new Contact()
                                .name("SaludPlus IT Team")
                                .email("soporte@saludplus.cl")
                                .url("https://saludplus.cl")));
    }
}
