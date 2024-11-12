# API de Gestión de Pausas

Este repositorio contiene la implementación de una API RESTful para gestionar las pausas en los viajes de monopatines. La API permite crear, obtener, eliminar pausas y administrar viajes.

## Endpoints

### 1. **Crear una pausa**
- **URL**: `/pausas/alta/{viajeId}`
- **Método HTTP**: `POST`
- **Descripción**: Crea una pausa para un viaje en específico, registrando el inicio y el fin de la pausa.
- **Parámetros de consulta**:
    - `horaInicio` (requerido): Hora de inicio de la pausa (formato: `yyyy-MM-dd'T'HH:mm:ss`).
    - `horaFin` (requerido): Hora de fin de la pausa (formato: `yyyy-MM-dd'T'HH:mm:ss`).
- **Respuesta exitosa**:
    - **Código de estado**: `201 Created`
    - **Cuerpo**: La pausa recién creada.
- **Respuesta con error**:
    - **Código de estado**: `400 Bad Request` si los parámetros de fecha no son válidos.
    - **Código de estado**: `404 Not Found` si el viaje no existe.

**Ejemplo de solicitud**:
   ```http
   POST /pausas/alta/1?horaInicio=2024-11-10T08:00:00&horaFin=2024-11-10T08:10:00
