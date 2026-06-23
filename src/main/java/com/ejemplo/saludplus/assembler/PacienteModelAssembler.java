package com.ejemplo.saludplus.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ejemplo.saludplus.controller.PacienteControllerV2;
import com.ejemplo.saludplus.controller.FichaPacienteController;
import com.ejemplo.saludplus.controller.AtencionController;
import com.ejemplo.saludplus.model.Paciente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PacienteModelAssembler implements RepresentationModelAssembler<Paciente, EntityModel<Paciente>> {

    @Override
    public EntityModel<Paciente> toModel(Paciente paciente) {
        return EntityModel.of(paciente,
                linkTo(methodOn(PacienteControllerV2.class).buscarPorId(paciente.getId())).withSelfRel(),
                linkTo(methodOn(PacienteControllerV2.class).obtenerPacientes()).withRel("pacientes"),
                linkTo(methodOn(FichaPacienteController.class).historialPaciente(paciente.getId())).withRel("historial-clinico"),
                linkTo(methodOn(AtencionController.class).reportePorPaciente(paciente.getId())).withRel("reporte-atenciones")
        );
    }
}
