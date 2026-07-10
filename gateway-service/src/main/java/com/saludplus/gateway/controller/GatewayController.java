package com.saludplus.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    // PACIENTES
    @GetMapping("/api/pacientes")
    public ResponseEntity<?> getPacientes() {
        return restTemplate.getForEntity("http://localhost:8081/api/pacientes", Object.class);
    }
    @GetMapping("/api/pacientes/{id}")
    public ResponseEntity<?> getPaciente(@PathVariable Long id) {
        return restTemplate.getForEntity("http://localhost:8081/api/pacientes/" + id, Object.class);
    }

    // MEDICOS
    @GetMapping("/api/medicos")
    public ResponseEntity<?> getMedicos() {
        return restTemplate.getForEntity("http://localhost:8082/api/medicos", Object.class);
    }
    @GetMapping("/api/medicos/{id}")
    public ResponseEntity<?> getMedico(@PathVariable Long id) {
        return restTemplate.getForEntity("http://localhost:8082/api/medicos/" + id, Object.class);
    }

    // CITAS
    @GetMapping("/api/citas")
    public ResponseEntity<?> getCitas() {
        return restTemplate.getForEntity("http://localhost:8083/api/citas", Object.class);
    }
    @GetMapping("/api/citas/{id}")
    public ResponseEntity<?> getCita(@PathVariable Long id) {
        return restTemplate.getForEntity("http://localhost:8083/api/citas/" + id, Object.class);
    }

    // ATENCIONES
    @GetMapping("/api/atenciones")
    public ResponseEntity<?> getAtenciones() {
        return restTemplate.getForEntity("http://localhost:8084/api/atenciones", Object.class);
    }

    // FICHAS
    @GetMapping("/api/fichas")
    public ResponseEntity<?> getFichas() {
        return restTemplate.getForEntity("http://localhost:8085/api/fichas", Object.class);
    }

    // PAGOS
    @GetMapping("/api/pagos")
    public ResponseEntity<?> getPagos() {
        return restTemplate.getForEntity("http://localhost:8086/api/pagos", Object.class);
    }

    // FARMACIA
    @GetMapping("/api/farmacia")
    public ResponseEntity<?> getFarmacia() {
        return restTemplate.getForEntity("http://localhost:8087/api/farmacia", Object.class);
    }

    // NOTIFICACIONES
    @GetMapping("/api/notificaciones")
    public ResponseEntity<?> getNotificaciones() {
        return restTemplate.getForEntity("http://localhost:8088/api/notificaciones", Object.class);
    }
}
