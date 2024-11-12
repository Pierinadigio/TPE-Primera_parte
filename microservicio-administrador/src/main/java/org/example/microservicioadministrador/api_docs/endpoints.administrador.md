# API Endpoints de Administrador

## Descripción general
Este microservicio está diseñado para gestionar las funciones administrativas, como la anulación de cuentas, la generación de reportes, ajustes de tarifas, gestión de monopatines y su ubicación en paradas.

---

## Endpoints de Administrador

### 1. Anular una cuenta
- **URL**: `/administrador/{cuentaId}/anular`
- **Método**: `PUT`
- **Descripción**: Anula una cuenta de usuario identificada por su `cuentaId`.
- **Parámetros de ruta**:
    - `cuentaId` (Long) - ID de la cuenta a anular.
- **Código de respuesta**: `204 No Content`

---

### 2. Consultar reporte de monopatines con más de X viajes
- **URL**: `/administrador/reporte/monopatines`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de los monopatines que han realizado más de X viajes durante un año específico.
- **Parámetros de consulta**:
    - `anio` (int) - Año del reporte.
    - `minViajes` (long) - Mínimo número de viajes realizados.
- **Código de respuesta**: `200 OK`

---

### 3. Consultar total facturado en un rango de meses
- **URL**: `/administrador/reporte/facturado`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de la cantidad total facturada en un rango de meses durante un año específico.
- **Parámetros de consulta**:
    - `anio` (int) - Año del reporte.
    - `mesInicio` (int) - Mes de inicio (1-12).
    - `mesFin` (int) - Mes de fin (1-12).
- **Código de respuesta**: `200 OK`

---

### 4. Consultar cantidad de monopatines en operación vs en mantenimiento
- **URL**: `/administrador/estado-monopatines`
- **Método**: `GET`
- **Descripción**: Consulta el estado actual de los monopatines en operación y en mantenimiento.
- **Código de respuesta**: `200 OK`

---

### 5. Realizar ajuste de tarifas a partir de cierta fecha
- **URL**: `/administrador/ajustes/tarifas`
- **Método**: `POST`
- **Descripción**: Ajusta las tarifas base y extra a partir de una fecha especificada.
- **Parámetros de consulta**:
    - `fechaAjuste` (LocalDate) - Fecha en la que se realiza el ajuste de tarifas.
    - `porcentajeBase` (double) - Porcentaje de ajuste sobre la tarifa base por minuto.
    - `porcentajeExtra` (double) - Porcentaje de ajuste sobre la tarifa extra por minuto.
- **Código de respuesta**: `200 OK` (si el ajuste es exitoso), `500 Internal Server Error` (si ocurre un error).

---

### 6. Ubicar un monopatín en una parada
- **URL**: `/administrador/viaje/{viajeId}/monopatin/{monopatinId}/parada/{paradaId}`
- **Método**: `POST`
- **Descripción**: Ubica un monopatín en una parada durante un viaje específico.
- **Parámetros de ruta**:
    - `viajeId` (Long) - ID del viaje en el que se ubicará el monopatín.
    - `monopatinId` (Long) - ID del monopatín a ubicar.
    - `paradaId` (Long) - ID de la parada en la que se ubicará el monopatín.
- **Código de respuesta**: `204 No Content`

---

### 7. Agregar un monopatín
- **URL**: `/administrador/monopatines`
- **Método**: `POST`
- **Descripción**: Agrega un nuevo monopatín al sistema.
- **Cuerpo de la solicitud**:
    - Se espera un objeto `MonopatinDTO` con los datos del monopatín:
        - `id`: ID del monopatín (auto-generado).
        - Otros campos relevantes como `modelo`, `estado`, `latitud`, `longitud`, etc.
- **Código de respuesta**: `200 OK`

---

### 8. Eliminar un monopatín
- **URL**: `/administrador/monopatines/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un monopatín del sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín a eliminar.
- **Código de respuesta**: `204 No Content`

---

### 9. Obtener un monopatín por ID
- **URL**: `/administrador/monopatines/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un monopatín específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín a consultar.
- **Código de respuesta**: `200 OK`
