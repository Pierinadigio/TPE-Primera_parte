# API Endpoints de Usuario

## Descripción general
Este microservicio gestiona las operaciones relacionadas con los usuarios, incluyendo la creación, actualización, eliminación, consultas y reportes relacionados con monopatines cercanos.

---

## Endpoints de Usuario

### 1. Crear múltiples usuarios
- **URL**: `/usuarios/crearUsuarios`
- **Método**: `POST`
- **Descripción**: Crea múltiples usuarios en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    [
        {
            "contrasenia": "password123",
            "nombre": "Juan",
            "apellido": "Perez",
            "celular": "1234567890",
            "email": "juan.perez@example.com",
            "latitud": 15,
            "longitud": 30
        },
        {
            "contrasenia": "password456",
            "nombre": "Maria",
            "apellido": "Lopez",
            "celular": "0987654321",
            "email": "maria.lopez@example.com",
            "latitud": 25,
            "longitud": 50
        }
    ]
    ```

---

### 2. Obtener todos los usuarios
- **URL**: `/usuarios`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todos los usuarios.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `UsuarioDTO`.

---

### 3. Crear un nuevo usuario
- **URL**: `/usuarios`
- **Método**: `POST`
- **Descripción**: Crea un nuevo usuario en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    {
        "contrasenia": "password123",
        "nombre": "Carlos",
        "apellido": "Sanchez",
        "celular": "9876543210",
        "email": "carlos.sanchez@example.com",
        "latitud": 40.712500,
        "longitud": 74.005500
    }
    ```
---

### 4. Obtener usuario por ID
- **URL**: `/usuarios/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un usuario específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del usuario.
- **Código de respuesta**: `200 OK` - Retorna el objeto `UsuarioDTO` correspondiente.

---

### 5. Actualizar usuario
- **URL**: `/usuarios/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza los detalles de un usuario existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID del usuario a actualizar.
- **Cuerpo de la solicitud**:
    ```json
    {
        "contrasenia": "newpassword123",
        "nombre": "Carlos",
        "apellido": "Sanchez",
        "celular": "9876543210",
        "email": "carlos.sanchez@newemail.com",
        "latitud": 40.712900,
        "longitud": -74.005600
    }
    ```
---

### 6. Eliminar usuario
- **URL**: `/usuarios/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un usuario del sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del usuario a eliminar.
- **Código de respuesta**: `204 No Content` - El usuario ha sido eliminado correctamente.

---

### 7. Obtener monopatines cercanos
- **URL**: `/usuarios/{usuarioId}/monopatines-cercanos`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de monopatines cercanos a un usuario, dentro de un radio especificado.
- **Parámetros de ruta**:
    - `usuarioId` (Long) - ID del usuario para obtener los monopatines cercanos.
- **Parámetros de consulta**:
    - `radio` (double, opcional, valor predeterminado: `1.0`) - Radio en kilómetros para buscar los monopatines cercanos.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ReporteMonopatinesCercanosDTO`.

---
