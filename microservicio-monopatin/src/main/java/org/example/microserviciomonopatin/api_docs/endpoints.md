# API Endpoints de Monopatín

## Descripción general
Este microservicio gestiona las operaciones relacionadas con los monopatines, incluyendo la creación, actualización, eliminación, consultas y reportes de uso.

---

## Endpoints de Monopatín

### 1. Crear un monopatín
- **URL**: `/monopatines`
- **Método**: `POST`
- **Descripción**: Crea un nuevo monopatín en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    {
        "modelo": "Modelo 6",
        "latitud": 25,
        "longitud": 50,
        "estado": "en-operacion",
        "disponible": true,
        "tiempoUsoConPausa": 120,
        "tiempoUsoSinPausa": 100,
        "totalKmRecorridos": 15.5
    }
    ```
- **Código de respuesta**: `201 Created` - El monopatín se ha creado correctamente.

---

### 2. Obtener monopatín por ID
- **URL**: `/monopatines/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un monopatín específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín.
- **Código de respuesta**: `200 OK` - Retorna el objeto `MonopatinDTO` correspondiente.

---

### 3. Actualizar monopatín
- **URL**: `/monopatines/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza los detalles de un monopatín existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín a actualizar.
- **Cuerpo de la solicitud**:
    ```json
    {
        "modelo": "ModeloX",
        "latitud": 40.715000,
        "longitud": -74.005000,
        "estado": "en-mantenimiento",
        "disponible": false,
        "tiempoUsoConPausa": 150,
        "tiempoUsoSinPausa": 130,
        "totalKmRecorridos": 18.5
    }
    ```
- **Código de respuesta**: `200 OK` - Retorna el objeto `MonopatinDTO` actualizado.

---

### 4. Eliminar monopatín
- **URL**: `/monopatines/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un monopatín del sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín a eliminar.
- **Código de respuesta**: `204 No Content` - El monopatín se ha eliminado correctamente.

---

### 5. Crear múltiples monopatines
- **URL**: `/monopatines/crearMonopatines`
- **Método**: `POST`
- **Descripción**: Crea múltiples monopatines en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    [
        {
            "modelo": "Modelo 7",
            "latitud": 40.712776,
            "longitud": 74.005974,
            "estado": "en-operacion",
            "disponible": true,
            "tiempoUsoConPausa": 120,
            "tiempoUsoSinPausa": 100,
            "totalKmRecorridos": 15.5
        },
        {
            "modelo": "Modelo 8",
            "latitud": 10,
            "longitud": 20,
            "estado": "en-operacion",
            "disponible": true,
            "tiempoUsoConPausa": 150,
            "tiempoUsoSinPausa": 120,
            "totalKmRecorridos": 20.3
        }
    ]
 
---

### 6. Obtener todos los monopatines
- **URL**: `/monopatines`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todos los monopatines.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `MonopatinDTO`.

---

### 7. Verificar si un monopatín existe
- **URL**: `/monopatines/monopatin/{id}`
- **Método**: `GET`
- **Descripción**: Verifica si un monopatín con el ID especificado existe.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín.
- **Código de respuesta**:
    - `true` si el monopatín existe.
    - `false` si el monopatín no existe.

---

### 8. Generar reporte de uso de monopatines
- **URL**: `/monopatines/reporteUso`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte del uso de los monopatines.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ReporteUsoDTO`.

---

### 9. Obtener reporte de uso por kilómetros recorridos
- **URL**: `/monopatines/reporteUso/km`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de uso de los monopatines basado en los kilómetros recorridos.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ReporteUsoDTO`.

---

### 10. Obtener reporte de tiempos con o sin pausa
- **URL**: `/monopatines/reporteUso/filtro-tiempos`
- **Método**: `GET`
- **Descripción**: Genera un reporte de los tiempos de uso de los monopatines, con un parámetro opcional para incluir o excluir los tiempos con pausa.
- **Parámetros de consulta**:
    - `includeSinPausa` (boolean, opcional, valor predeterminado: `true`) - Si es `true`, incluye tiempos sin pausa; si es `false`, los excluye.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ReporteUsoDTO`.

---

### 11. Obtener el reporte de estado de monopatines (operación vs mantenimiento)
- **URL**: `/monopatines/estado/reporte`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte con la cantidad de monopatines en operación y en mantenimiento.
- **Código de respuesta**: `200 OK` - Retorna un mapa con los estados y la cantidad de monopatines.
