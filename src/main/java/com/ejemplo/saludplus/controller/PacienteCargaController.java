package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.dto.PacienteDTO;
import com.ejemplo.saludplus.service.CargaMasivaPacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteCargaController {

    private final CargaMasivaPacienteService service;

    public PacienteCargaController(CargaMasivaPacienteService service) {
        this.service = service;
    }

    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<PacienteDTO> lista) {

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.badRequest().body("Lista vacía");
        }

        long inicio = System.currentTimeMillis();

        service.procesarCarga(lista);

        long fin = System.currentTimeMillis();

        return ResponseEntity.ok("Se cargaron " + lista.size() + " pacientes en " + (fin - inicio) + " ms");
    }
}