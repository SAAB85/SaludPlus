package com.ejemplo.saludplus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @GetMapping("/admin/test")
    public String testAdmin() {
        return "Acceso permitido: admin/test funcionando";
    }
}