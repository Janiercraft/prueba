# Impacto Backend

Backend REST profesional para una fundación que necesita trazabilidad de familias, niños, necesidades, donaciones, recursos, asignaciones y entregas.

## Stack

Java 21, Spring Boot 3.5.16, Maven, PostgreSQL, Spring Data JPA/Hibernate, Bean Validation, Flyway, MapStruct, JUnit 5, Mockito, Testcontainers, Docker, OpenAPI/Swagger y SLF4J/Logback. Sin autenticación JWT: la API queda abierta para facilitar el desarrollo y las pruebas.

Spring Boot 3.5.16 requiere al menos Java 17 y es compatible con Java 21.

## Arquitectura

El proyecto usa un monolito modular con separación `api`, `application`, `domain`, `infrastructure` y `config`.

- `api`: controllers, DTOs, mappers y errores HTTP.
- `application`: casos de uso y reglas transaccionales.
- `domain`: entidades y enums del negocio.
- `infrastructure`: persistencia y almacenamiento.
- `config`: configuración, OpenAPI y seed de desarrollo.

Las entidades JPA no se exponen directamente: la API trabaja con DTOs.

## Variables de entorno

Copia `.env.example` como referencia. Variables principales:

`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `CORS_ALLOWED_ORIGINS`, `STORAGE_PATH`.

## Ejecutar localmente

1. Tener Java 21 y PostgreSQL.
2. Crear la base `impacto` y un usuario con permisos.
3. Configurar variables de entorno o utilizar los defaults de desarrollo.
4. Ejecutar `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`.

Flyway crea el esquema automáticamente; Hibernate usa `ddl-auto=validate` y no crea tablas en producción.

## Docker

Construir primero el JAR:

```bash
./mvnw clean package
```

Luego:

```bash
docker compose up --build
```

El backend queda en `http://localhost:8080` y PostgreSQL en `localhost:5432`.

## Swagger

`http://localhost:8080/swagger-ui.html`

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Usuarios de desarrollo

Sólo al arrancar con perfil `dev` y base vacía se crean usuarios de demostración:

- `admin@impacto.local` / `Admin123!`
- `volunteer@impacto.local` / `Volunteer123!`
- `donor@impacto.local` / `Donor123!`

Son credenciales ficticias y deben cambiarse fuera de desarrollo.

## Flujo principal

Donación MATERIAL → recurso → asignación a necesidad → entrega a voluntario → entrega COMPLETED → recurso DELIVERED + necesidad actualizada → trazabilidad.

Las donaciones MONEY se almacenan sin generar inventario físico.

## API principal

- Familias: `/api/v1/families`
- Niños: `/api/v1/children`
- Necesidades: `/api/v1/needs`
- Donantes: `/api/v1/donors`
- Campañas: `/api/v1/campaigns`
- Donaciones: `/api/v1/donations`
- Recursos: `/api/v1/resources`
- Asignaciones: `/api/v1/assignments`
- Entregas: `/api/v1/deliveries`
- Voluntario: `/api/v1/volunteer/tasks`
- Donante: `/api/v1/donor/donations`
- Dashboard: `/api/v1/dashboard`
- Trazabilidad: `/api/v1/resources/{id}/traceability`

## Tests

```bash
./mvnw clean test
```

El proyecto incluye tests de contexto y pruebas de integración con Testcontainers. Para ejecutar integración con PostgreSQL Docker debe estar disponible.

## Migraciones

Las migraciones están en `src/main/resources/db/migration` y siguen el esquema `Vn__descripcion.sql`.

## Seguridad

CORS configurable, validación de datos y reglas de negocio. No se aplica autenticación ni autorización HTTP en esta versión; los campos de rol y usuario se conservan como parte del modelo de datos.

## Decisiones relevantes

- Modular monolith para reducir complejidad operativa durante la hackathon.
- UUID en entidades.
- Auditoría con Spring Data Auditing.
- Transacciones en asignación y cierre de entrega.
- StorageService abstrae el almacenamiento para poder migrar a S3, Cloudinary, GCS o Azure Blob.
- Paginación y ordenamiento para colecciones grandes.
- Respuesta de error uniforme mediante `@RestControllerAdvice`.

## Producción: reforzamientos pendientes

Para un despliegue real conviene añadir autenticación/autorización, gestión de secretos, observabilidad centralizada, rate limiting, antivirus/validación MIME de archivos, almacenamiento de objetos externo y una política formal de retención de PII. La base implementada es funcional para la hackathon, pero esos controles dependen del entorno real de producción.

## Requisito de Java

El proyecto compila a **Java 21** (`<java.version>21</java.version>`). Puedes ejecutar Maven con un JDK más nuevo siempre que el compilador use `release 21`; el test de versión valida el bytecode generado, no la versión del JDK que ejecuta Maven.

Para comprobar tu entorno:

```powershell
java -version
mvn -version
```

El `IntegrationPostgresTest` requiere Docker. Si Docker no está disponible, el test se omite automáticamente.
