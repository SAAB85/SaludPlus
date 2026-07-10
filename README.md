# 🏥 SaludPlus — Sistema de Gestión Clínica con Microservicios

Sistema backend desarrollado con **Spring Boot** para la gestión de una clínica médica, migrado desde una arquitectura monolítica a una arquitectura distribuida basada en **microservicios independientes**.

---

## 👥 Integrantes del Equipo

| Nombre | Rol |
|--------|-----|
| Sebastián Antipán | Desarrollador Full Stack / Arquitecto |

---

## 📌 Descripción

**SaludPlus** es una API RESTful distribuida que permite administrar los principales recursos de un sistema clínico mediante microservicios independientes:

- Registro y gestión de **pacientes**
- Gestión de **médicos**
- Reserva y cancelación de **citas médicas**
- Registro de **atenciones médicas**
- Administración de **fichas clínicas**
- Gestión de **pagos**
- Control de **farmacia y medicamentos**
- Sistema de **notificaciones**
- **API Gateway** como punto de entrada unificado

---

## 🛠️ Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.5 |
| Spring Web | — |
| Spring Data JPA | — |
| Spring Security | — |
| Spring HATEOAS | — |
| Spring Cloud Gateway | — |
| MySQL Connector | — |
| Flyway | — |
| Lombok | — |
| Bean Validation | — |
| JJWT (JWT) | 0.11.5 |
| SpringDoc OpenAPI (Swagger) | 2.6.0 |

---

## 🏗️ Arquitectura de Microservicios

```
                    ┌─────────────────────┐
                    │   API Gateway        │
                    │   Puerto: 8080       │
                    └──────────┬──────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
┌───────▼──────┐    ┌──────────▼──────┐   ┌──────────▼──────┐
│  pacientes   │    │    medicos      │   │     citas       │
│  Puerto:8081 │    │  Puerto: 8082   │   │  Puerto: 8083   │
│ db_pacientes │    │   db_medicos    │   │    db_citas     │
└──────────────┘    └─────────────────┘   └─────────────────┘
        │                      │                      │
┌───────▼──────┐    ┌──────────▼──────┐   ┌──────────▼──────┐
│  atenciones  │    │     fichas      │   │     pagos       │
│  Puerto:8084 │    │  Puerto: 8085   │   │  Puerto: 8086   │
│db_atenciones │    │   db_fichas     │   │    db_pagos     │
└──────────────┘    └─────────────────┘   └─────────────────┘
        │                      │
┌───────▼──────┐    ┌──────────▼──────┐
│   farmacia   │    │ notificaciones  │
│  Puerto:8087 │    │  Puerto: 8088   │
│  db_farmacia │    │db_notificaciones│
└──────────────┘    └─────────────────┘
```

Cada microservicio sigue el patrón **CSR (Controller → Service → Repository)** con su propia base de datos MySQL independiente.

---

## 📦 Microservicios Implementados

| # | Microservicio | Puerto | Base de Datos | Swagger |
|---|--------------|--------|---------------|---------|
| 1 | gateway-service | 8080 | — | — |
| 2 | pacientes-service | 8081 | db_pacientes | http://localhost:8081/swagger-ui.html |
| 3 | medicos-service | 8082 | db_medicos | http://localhost:8082/swagger-ui.html |
| 4 | citas-service | 8083 | db_citas | http://localhost:8083/swagger-ui.html |
| 5 | atenciones-service | 8084 | db_atenciones | http://localhost:8084/swagger-ui.html |
| 6 | fichas-service | 8085 | db_fichas | http://localhost:8085/swagger-ui.html |
| 7 | pagos-service | 8086 | db_pagos | http://localhost:8086/swagger-ui.html |
| 8 | farmacia-service | 8087 | db_farmacia | http://localhost:8087/swagger-ui.html |
| 9 | notificaciones-service | 8088 | db_notificaciones | http://localhost:8088/swagger-ui.html |
| 10 | saludplus (monolito base) | — | db_hospital_vm | — |

---

## 🔌 Endpoints Principales por Microservicio

### 👥 Pacientes (8081)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/pacientes` | Listar todos los pacientes |
| `GET` | `/api/pacientes/{id}` | Obtener paciente por ID |
| `POST` | `/api/pacientes` | Crear nuevo paciente |
| `PUT` | `/api/pacientes/{id}` | Actualizar paciente |
| `DELETE` | `/api/pacientes/{id}` | Eliminar paciente |

### 🩺 Médicos (8082)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/medicos` | Listar todos los médicos |
| `GET` | `/api/medicos/{id}` | Obtener médico por ID |
| `POST` | `/api/medicos` | Crear nuevo médico |
| `PUT` | `/api/medicos/{id}` | Actualizar médico |
| `DELETE` | `/api/medicos/{id}` | Eliminar médico |

### 📅 Citas (8083)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/citas` | Listar todas las citas |
| `GET` | `/api/citas/{id}` | Obtener cita por ID |
| `POST` | `/api/citas` | Reservar nueva cita |
| `PUT` | `/api/citas/{id}` | Actualizar cita |
| `DELETE` | `/api/citas/{id}` | Cancelar cita |

### 📋 Atenciones (8084)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/atenciones` | Listar todas las atenciones |
| `GET` | `/api/atenciones/{id}` | Obtener atención por ID |
| `POST` | `/api/atenciones` | Registrar nueva atención |
| `PUT` | `/api/atenciones/{id}` | Actualizar atención |
| `DELETE` | `/api/atenciones/{id}` | Eliminar atención |

### 📁 Fichas (8085)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/fichas` | Listar fichas |
| `GET` | `/api/fichas/{id}` | Obtener ficha por ID |
| `POST` | `/api/fichas` | Crear ficha |
| `PUT` | `/api/fichas/{id}` | Actualizar ficha |
| `DELETE` | `/api/fichas/{id}` | Eliminar ficha |

### 💰 Pagos (8086)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/pagos` | Listar todos los pagos |
| `GET` | `/api/pagos/{id}` | Obtener pago por ID |
| `POST` | `/api/pagos` | Registrar nuevo pago |
| `PUT` | `/api/pagos/{id}` | Actualizar pago |
| `DELETE` | `/api/pagos/{id}` | Eliminar pago |

### 💊 Farmacia (8087)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/farmacia` | Listar medicamentos |
| `GET` | `/api/farmacia/{id}` | Obtener medicamento por ID |
| `POST` | `/api/farmacia` | Agregar medicamento |
| `PUT` | `/api/farmacia/{id}` | Actualizar medicamento |
| `DELETE` | `/api/farmacia/{id}` | Eliminar medicamento |

### 🔔 Notificaciones (8088)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/notificaciones` | Listar notificaciones |
| `GET` | `/api/notificaciones/{id}` | Obtener notificación por ID |
| `POST` | `/api/notificaciones` | Crear notificación |
| `PUT` | `/api/notificaciones/{id}` | Actualizar notificación |
| `DELETE` | `/api/notificaciones/{id}` | Eliminar notificación |

---

## ⚙️ Configuración y Ejecución Local

### Prerrequisitos
- Java 17+
- Maven 3.8+
- MySQL 8+ (Laragon recomendado)

### 1. Clonar el repositorio
```bash
git clone https://github.com/SAAB85/SaludPlus.git
cd SaludPlus
```

### 2. Crear las bases de datos en MySQL
```sql
CREATE DATABASE IF NOT EXISTS db_hospital_vm;
CREATE DATABASE IF NOT EXISTS db_pacientes;
CREATE DATABASE IF NOT EXISTS db_medicos;
CREATE DATABASE IF NOT EXISTS db_citas;
CREATE DATABASE IF NOT EXISTS db_atenciones;
CREATE DATABASE IF NOT EXISTS db_fichas;
CREATE DATABASE IF NOT EXISTS db_pagos;
CREATE DATABASE IF NOT EXISTS db_farmacia;
CREATE DATABASE IF NOT EXISTS db_notificaciones;
```

### 3. Ejecutar cada microservicio
Abrir una terminal por cada microservicio y ejecutar:

```bash
# Terminal 1 - Pacientes
cd pacientes-service
./mvnw spring-boot:run

# Terminal 2 - Médicos
cd medicos-service
./mvnw spring-boot:run

# Terminal 3 - Citas
cd citas-service
./mvnw spring-boot:run

# Terminal 4 - Atenciones
cd atenciones-service
./mvnw spring-boot:run

# Terminal 5 - Fichas
cd fichas-service
./mvnw spring-boot:run

# Terminal 6 - Pagos
cd pagos-service
./mvnw spring-boot:run

# Terminal 7 - Farmacia
cd farmacia-service
./mvnw spring-boot:run

# Terminal 8 - Notificaciones
cd notificaciones-service
./mvnw spring-boot:run

# Terminal 9 - Gateway
cd gateway-service
./mvnw spring-boot:run
```

---

## 🗄️ Migraciones de Base de Datos

El proyecto base utiliza **Flyway** para gestionar el esquema automáticamente.

| Versión | Script | Descripción |
|---------|--------|-------------|
| V1 | `V1__create_paciente_table.sql` | Tabla de pacientes |
| V2 | `V2__create_usuario_table.sql` | Tabla de usuarios |
| V3 | `V3__create_tipo_usuario_table.sql` | Tipos de usuario |
| V4 | `V4__create_medico_table.sql` | Tabla de médicos |
| V5 | `V5__alter_paciente_add_tipo_usuario.sql` | Relación paciente-tipo |
| V6 | `V6__create_atencion_table.sql` | Tabla de atenciones |
| V7 | `V7__create_ficha_paciente_table.sql` | Fichas de paciente |

---

## 📖 Documentación Swagger

Cada microservicio expone su propia documentación Swagger:

| Microservicio | URL Swagger |
|--------------|-------------|
| Pacientes | http://localhost:8081/swagger-ui.html |
| Médicos | http://localhost:8082/swagger-ui.html |
| Citas | http://localhost:8083/swagger-ui.html |
| Atenciones | http://localhost:8084/swagger-ui.html |
| Fichas | http://localhost:8085/swagger-ui.html |
| Pagos | http://localhost:8086/swagger-ui.html |
| Farmacia | http://localhost:8087/swagger-ui.html |
| Notificaciones | http://localhost:8088/swagger-ui.html |

---

## 🔒 Seguridad

La API implementa autenticación basada en **JSON Web Tokens (JWT)**:

1. El cliente realiza `POST /auth/login` con sus credenciales
2. El servidor devuelve un token JWT
3. El cliente incluye el token en cada petición:
```
Authorization: Bearer <token>
```

---

## 📄 Licencia

Proyecto académico universitario — DUOC UC, Ingeniería en Informática.