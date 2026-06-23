package com.ejemplo.saludplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
@Schema(description = "Modelo de datos que representa a un Usuario del sistema (Credenciales)")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre de usuario para iniciar sesión", example = "admin")
    private String username;

    @Schema(description = "Contraseña encriptada del usuario", example = "$2a$10$xyz...")
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Rol del usuario en el sistema", example = "ROLE_ADMIN")
    private Role role;

    public User() {}

    public Long getId() {
        return id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}