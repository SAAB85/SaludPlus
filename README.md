# 🏥 SaludPlus — API REST de Gestión Hospitalaria

Sistema backend desarrollado con **Spring Boot** para la gestión de pacientes, médicos, atenciones y fichas clínicas en un entorno hospitalario. Incluye autenticación JWT, documentación Swagger y migraciones de base de datos con Flyway.

---

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints de la API](#endpoints-de-la-api)
- [Configuración y Ejecución](#configuración-y-ejecución)
- [Migraciones de Base de Datos](#migraciones-de-base-de-datos)
- [Documentación Swagger](#documentación-swagger)
- [Seguridad](#seguridad)

---

## 📌 Descripción

**SaludPlus** es una API RESTful que permite administrar los principales recursos de un sistema hospitalario:

- Registro y gestión de **pacientes**
- Gestión de **médicos**
- Registro de **atenciones médicas**
- Administración de **fichas de pacientes**
- Control de acceso mediante autenticación **JWT**
- API versionada (v1 y v2 con soporte **HATEOAS**)

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
| MySQL Connector | — |
| Flyway | — |
| Lombok | — |
| Bean Validation | — |
| JJWT (JWT) | 0.11.5 |
| SpringDoc OpenAPI (Swagger) | 2.6.0 |
| DataFaker | 2.3.0 |

---

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas estándar de Spring Boot:

```
┌─────────────────────────────────────┐
│           Controller Layer          │  ← REST Controllers (v1 y v2)
├─────────────────────────────────────┤
│            Service Layer            │  ← Lógica de negocio
├─────────────────────────────────────┤
│          Repository Layer           │  ← Spring Data JPA
├─────────────────────────────────────┤
│             Model Layer             │  ← Entidades JPA
├─────────────────────────────────────┤
│          Security Layer             │  ← JWT + Spring Security
├─────────────────────────────────────┤
│        Base de Datos MySQL          │  ← Migraciones con Flyway
└─────────────────────────────────────┘
```

---

## 📁 Estructura del Proyecto

```
saludplus/
├── src/
│   └── main/
│       ├── java/com/ejemplo/saludplus/
│       │   ├── assembler/          # HATEOAS assemblers
│       │   ├── config/             # Configuración de la app
│       │   ├── controller/         # Controladores REST (v1 y v2)
│       │   │   ├── AuthController.java
│       │   │   ├── PacienteController.java
│       │   │   ├── PacienteControllerV2.java
│       │   │   ├── MedicoController.java
│       │   │   ├── MedicoControllerV2.java
│       │   │   ├── AtencionController.java
│       │   │   ├── AtencionControllerV2.java
│       │   │   ├── FichaPacienteController.java
│       │   │   └── FichaPacienteControllerV2.java
│       │   ├── dto/                # Data Transfer Objects
│       │   ├── model/              # Entidades JPA
│       │   │   ├── Paciente.java
│       │   │   ├── Medico.java
│       │   │   ├── Atencion.java
│       │   │   ├── FichaPaciente.java
│       │   │   ├── User.java
│       │   │   ├── TipoUsuario.java
│       │   │   └── Role.java
│       │   ├── repository/         # Interfaces JPA Repository
│       │   ├── security/           # JWT + Spring Security
│       │   │   ├── JwtService.java
│       │   │   ├── JwtFilter.java
│       │   │   └── CustomUserDetailsService.java
│       │   ├── service/            # Servicios de negocio
│       │   ├── DataLoader.java     # Carga de datos iniciales
│       │   └── SaludplusApplication.java
│       └── resources/
│           ├── application.properties
│           └── db/migration/       # Scripts SQL Flyway
│               ├── V1__create_paciente_table.sql
│               ├── V2__create_usuario_table.sql
│               ├── V3__create_tipo_usuario_table.sql
│               ├── V4__create_medico_table.sql
│               ├── V5__alter_paciente_add_tipo_usuario.sql
│               ├── V6__create_atencion_table.sql
│               └── V7__create_ficha_paciente_table.sql
└── pom.xml
```

---

## 🔌 Endpoints de la API

### 🔐 Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/auth/login` | Iniciar sesión y obtener token JWT |

### 👥 Pacientes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/pacientes` | Listar todos los pacientes |
| `GET` | `/api/pacientes/{id}` | Obtener paciente por ID |
| `POST` | `/api/pacientes` | Crear nuevo paciente |
| `PUT` | `/api/pacientes/{id}` | Actualizar paciente |
| `DELETE` | `/api/pacientes/{id}` | Eliminar paciente |

### 🩺 Médicos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/medicos` | Listar todos los médicos |
| `GET` | `/api/medicos/{id}` | Obtener médico por ID |
| `POST` | `/api/medicos` | Crear nuevo médico |
| `PUT` | `/api/medicos/{id}` | Actualizar médico |
| `DELETE` | `/api/medicos/{id}` | Eliminar médico |

### 📋 Atenciones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/atenciones` | Listar todas las atenciones |
| `GET` | `/api/atenciones/{id}` | Obtener atención por ID |
| `POST` | `/api/atenciones` | Registrar nueva atención |
| `PUT` | `/api/atenciones/{id}` | Actualizar atención |
| `DELETE` | `/api/atenciones/{id}` | Eliminar atención |

### 📁 Fichas de Paciente

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/fichas` | Listar fichas |
| `GET` | `/api/fichas/{id}` | Obtener ficha por ID |
| `POST` | `/api/fichas` | Crear ficha |
| `PUT` | `/api/fichas/{id}` | Actualizar ficha |
| `DELETE` | `/api/fichas/{id}` | Eliminar ficha |

> 💡 Los endpoints v2 (`/api/v2/...`) incluyen soporte **HATEOAS** con enlaces hipermedia.

---

## ⚙️ Configuración y Ejecución

### Prerrequisitos

- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/saludplus.git
cd saludplus
```

### 2. Configurar la base de datos

Crea la base de datos en MySQL:

```sql
CREATE DATABASE db_hospital_vm;
```

### 3. Configurar `application.properties`

Edita el archivo `src/main/resources/application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_hospital_vm
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
```

### 4. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

O con Maven instalado:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 🗄️ Migraciones de Base de Datos

El proyecto utiliza **Flyway** para gestionar el esquema de la base de datos de forma automática. Las migraciones se ejecutan al iniciar la aplicación.

| Versión | Script | Descripción |
|---------|--------|-------------|
| V1 | `V1__create_paciente_table.sql` | Tabla de pacientes |
| V2 | `V2__create_usuario_table.sql` | Tabla de usuarios |
| V3 | `V3__create_tipo_usuario_table.sql` | Tabla de tipos de usuario |
| V4 | `V4__create_medico_table.sql` | Tabla de médicos |
| V5 | `V5__alter_paciente_add_tipo_usuario.sql` | Relación paciente-tipo usuario |
| V6 | `V6__create_atencion_table.sql` | Tabla de atenciones |
| V7 | `V7__create_ficha_paciente_table.sql` | Tabla de fichas de paciente |

---

## 📖 Documentación Swagger

Una vez ejecutada la aplicación, accede a la documentación interactiva de la API:

| Recurso | URL |
|---------|-----|
| Swagger UI | `http://localhost:8080/doc/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |

---

## 🔒 Seguridad

La API implementa autenticación basada en **JSON Web Tokens (JWT)**:

1. El cliente realiza `POST /auth/login` con sus credenciales
2. El servidor devuelve un token JWT
3. El cliente incluye el token en el header de cada petición:
   ```
   Authorization: Bearer <token>
   ```

### Roles disponibles

| Rol | Acceso |
|-----|--------|
| `ADMIN` | Acceso completo |
| `USER` | Acceso de lectura / operaciones básicas |

---

## 👨‍💻 Autor

Desarrollado como proyecto universitario de gestión hospitalaria con arquitectura RESTful y buenas prácticas de Spring Boot.

---

## 📄 Licencia

Este proyecto es de uso académico y educativo.