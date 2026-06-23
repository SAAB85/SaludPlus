package com.example.citasservice.service;

import com.example.citasservice.dto.PacienteDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteClientService {

    private final RestTemplate restTemplate;

    public PacienteClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PacienteDTO obtenerPaciente(Long id, String token) {

        String url = "http://localhost:8080/pacientes/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<PacienteDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                PacienteDTO.class
        );

        return response.getBody();
    }
}