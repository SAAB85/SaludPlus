package com.ejemplo.saludplus.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ejemplo.saludplus.controller.AtencionControllerV2;
import com.ejemplo.saludplus.model.Atencion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AtencionModelAssembler implements RepresentationModelAssembler<Atencion, EntityModel<Atencion>> {

    @Override
    public EntityModel<Atencion> toModel(Atencion atencion) {
        return EntityModel.of(atencion,
                linkTo(methodOn(AtencionControllerV2.class).obtenerPorId(atencion.getId())).withSelfRel(),
                linkTo(methodOn(AtencionControllerV2.class).obtenerTodas()).withRel("atenciones"),
                linkTo(methodOn(AtencionControllerV2.class).reportePorPaciente(atencion.getPaciente().getId())).withRel("atenciones-del-paciente"),
                linkTo(methodOn(AtencionControllerV2.class).reportePorMedico(atencion.getMedico().getIdMedico())).withRel("atenciones-del-medico")
        );
    }
}
