package com.ejemplo.saludplus.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ejemplo.saludplus.controller.FichaPacienteControllerV2;
import com.ejemplo.saludplus.controller.PacienteControllerV2;
import com.ejemplo.saludplus.model.FichaPaciente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FichaPacienteModelAssembler implements RepresentationModelAssembler<FichaPaciente, EntityModel<FichaPaciente>> {

    @Override
    public EntityModel<FichaPaciente> toModel(FichaPaciente ficha) {
        return EntityModel.of(ficha,
                linkTo(methodOn(FichaPacienteControllerV2.class).historialPaciente(ficha.getIdPaciente())).withSelfRel(),
                linkTo(methodOn(FichaPacienteControllerV2.class).obtenerTodas()).withRel("fichas"),
                linkTo(methodOn(PacienteControllerV2.class).buscarPorId(ficha.getIdPaciente())).withRel("paciente")
        );
    }
}
