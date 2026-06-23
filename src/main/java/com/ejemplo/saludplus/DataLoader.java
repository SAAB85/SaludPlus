package com.ejemplo.saludplus;

import com.ejemplo.saludplus.model.Atencion;
import com.ejemplo.saludplus.model.FichaPaciente;
import com.ejemplo.saludplus.model.Medico;
import com.ejemplo.saludplus.model.Paciente;
import com.ejemplo.saludplus.model.TipoUsuario;
import com.ejemplo.saludplus.repository.AtencionRepository;
import com.ejemplo.saludplus.repository.FichaPacienteRepository;
import com.ejemplo.saludplus.repository.MedicoRepository;
import com.ejemplo.saludplus.repository.PacienteRepository;
import com.ejemplo.saludplus.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final FichaPacienteRepository fichaPacienteRepository;
    private final AtencionRepository atencionRepository;

    @Override
    public void run(String... args) throws Exception {

        // Si ya existen datos, no volver a insertar (idempotencia)
        if (tipoUsuarioRepository.count() > 0) {
            log.info("⏭️  DataLoader omitido: los datos de prueba ya existen en la base de datos.");
            return;
        }

        Faker faker = new Faker();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Generar tipos de usuario (previsiones)
        String[] previsiones = {"FONASA", "ISAPRE", "CAPREDENA", "DIPRECA", "PARTICULAR"};
        for (String prevision : previsiones) {
            TipoUsuario tipo = new TipoUsuario();
            tipo.setNombre(prevision);
            tipoUsuarioRepository.save(tipo);
        }

        List<TipoUsuario> tipos = tipoUsuarioRepository.findAll();

        // Generar 50 pacientes
        for (int i = 0; i < 50; i++) {
            Paciente paciente = new Paciente();
            paciente.setRut(faker.idNumber().valid());
            paciente.setNombre(faker.name().fullName());
            paciente.setEdad(random.nextInt(1, 90));
            paciente.setTelefono("+569" + random.nextInt(10000000, 99999999));
            paciente.setTipoUsuario(tipos.get(random.nextInt(tipos.size())));
            pacienteRepository.save(paciente);
        }

        // Generar 10 médicos
        String[] especialidades = {
            "Medicina General", "Cardiología", "Pediatría",
            "Traumatología", "Neurología", "Ginecología",
            "Dermatología", "Oftalmología", "Psiquiatría", "Oncología"
        };
        for (int i = 0; i < 10; i++) {
            Medico medico = new Medico();
            medico.setRunMedico(faker.idNumber().valid());
            medico.setNombreCompleto("Dr. " + faker.name().fullName());
            medico.setEspecialidad(especialidades[i]);
            medico.setJefeTurno(i % 3 == 0 ? 'S' : 'N');
            medicoRepository.save(medico);
        }

        List<Paciente> pacientes = pacienteRepository.findAll();
        List<Medico> medicos = medicoRepository.findAll();

        // Generar ficha clínica para cada paciente
        String[] gruposSanguineos = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        for (Paciente paciente : pacientes) {
            FichaPaciente ficha = new FichaPaciente();
            ficha.setPaciente(paciente);
            ficha.setDatosPersonales(faker.observation().symptom());
            ficha.setDatosPersonales2(faker.disease().anyDisease());
            ficha.setDatosPersonales3("Grupo sanguíneo: " + gruposSanguineos[random.nextInt(gruposSanguineos.length)]);
            ficha.setDatosPersonales4(faker.medication().drugName());
            ficha.setDatosPersonales5(faker.lorem().sentence());
            fichaPacienteRepository.save(ficha);
        }

        // Generar 100 atenciones médicas
        int[] minutos = {0, 15, 30, 45};
        for (int i = 0; i < 100; i++) {
            Atencion atencion = new Atencion();
            atencion.setPaciente(pacientes.get(random.nextInt(pacientes.size())));
            atencion.setMedico(medicos.get(random.nextInt(medicos.size())));
            atencion.setFechaAtencion(LocalDate.now().minusDays(random.nextLong(365)));
            atencion.setHoraAtencion(LocalTime.of(
                    random.nextInt(8, 18),
                    minutos[random.nextInt(minutos.length)]));
            atencion.setCosto(random.nextInt(15000, 120000));
            atencion.setComentario(faker.lorem().sentence(10));
            atencionRepository.save(atencion);
        }

        log.info("✅ DataLoader ejecutado: datos de prueba generados correctamente.");
    }
}
