package com.ejemplo.saludplus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta que contiene el token JWT al iniciar sesión exitosamente")
public class AuthResponse {

    @Schema(description = "Token JWT generado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyMzg4MDAwMCwiZXhwIjoxNjIzODg3MjAwfQ...")
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}