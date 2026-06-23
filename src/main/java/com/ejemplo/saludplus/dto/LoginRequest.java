package com.ejemplo.saludplus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud de inicio de sesión")
public class LoginRequest {

    @Schema(description = "Nombre del usuario", example = "admin")
    private String username;

    @Schema(description = "Contraseña secreta del usuario", example = "1234")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}