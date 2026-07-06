# MS-Resenas

Microservicio encargado de administrar resenas, comentarios y calificaciones de usuarios sobre juegos.

## Responsabilidad

- Crear resenas de juegos.
- Consultar resenas por usuario.
- Consultar resenas por juego.
- Consultar resena por usuario y juego.
- Actualizar y eliminar resenas.
- Validar existencia de usuario y juego.

## Datos tecnicos

| Item | Valor |
| --- | --- |
| Puerto | `8086` |
| Base de datos | `resenas_db` |
| Ruta base | `/api/v2/resenas` |
| Swagger | `http://localhost:8086/doc/swagger-ui.html` |
| Eureka name | `ms-resenas` |

## Endpoints principales

- `GET /api/v2/resenas`
- `GET /api/v2/resenas/{id}`
- `GET /api/v2/resenas/juego/{juegoId}`
- `GET /api/v2/resenas/usuario/{usuarioId}`
- `GET /api/v2/resenas/usuario/{usuarioId}/juego/{juegoId}`
- `POST /api/v2/resenas`
- `PUT /api/v2/resenas/{id}`
- `DELETE /api/v2/resenas/{id}`

## Comunicacion

- Usa Feign Client para consultar juegos en `ms-juegos`.
- Usa WebClient para consultar usuarios en `ms-usuario`.
- Se registra en Eureka.

## Ejecucion local

```bash
./mvnw spring-boot:run
```

## Ejecucion con Docker

Desde la repo `Infraestructura`:

```bash
docker compose up -d --build ms-resenas
```

