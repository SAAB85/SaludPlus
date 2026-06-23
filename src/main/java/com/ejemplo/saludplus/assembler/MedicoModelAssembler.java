package com.ejemplo.saludplus.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ejemplo.saludplus.controller.MedicoControllerV2;
import com.ejemplo.saludplus.model.Medico;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MedicoModelAssembler implements RepresentationModelAssembler<Medico, EntityModel<Medico>> {

    @Override
    public EntityModel<Medico> toModel(Medico medico) {
        return EntityModel.of(medico,
                linkTo(methodOn(MedicoControllerV2.class).obtenerPorId(medico.getIdMedico())).withSelfRel(),
                linkTo(methodOn(MedicoControllerV2.class).obtenerTodos()).withRel("medicos"),
                linkTo(methodOn(MedicoControllerV2.class).reportePorEspecialidad(medico.getEspecialidad())).withRel("por-especialidad")
        );
    }
}
