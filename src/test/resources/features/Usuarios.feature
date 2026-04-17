# language: es
Característica: Gestión de Usuarios
  Como administrador del sistema
  Quiero poder gestionar el ciclo de vida de los usuarios (Crear, Consultar)
  Para mantener un control sobre quién tiene acceso al sistema

  @Critical
  Escenario: Creación exitosa de usuario con datos fijos (Hardcoded)
    Dado que tengo los datos para un nuevo usuario llamado "Wilson Valencia" con username "wilsonv" y correo "wilson@ejemplo.com"
    Cuando envío la petición para crear el usuario
    Entonces la respuesta debe tener el código de estado 201
    Y la respuesta debe contener el id generado, el nombre "Wilson Valencia", username "wilsonv" y correo "wilson@ejemplo.com"

  @Critical
  Escenario: Creación exitosa de usuario con datos dinámicos (DataFaker)
    Dado que genero datos aleatorios para un nuevo usuario
    Cuando envío la petición para crear el usuario
    Entonces la respuesta debe tener el código de estado 201
    Y la respuesta debe contener el id generado y los datos enviados coinciden

  @Critical
  Escenario: Obtener usuario que no existe
    Cuando intento consultar el usuario con id "9999"
    Entonces la respuesta debe tener el código de estado 404
    Y el correo del usuario debe ser vacío

  @Negative
  Escenario: Creación de usuario con correo ya registrado
    Dado que tengo los datos para un nuevo usuario llamado "Usuario Duplicado" con username "duplicado" y correo "duplicado@ejemplo.com"
    Y el usuario ya se encuentra registrado previamente en el sistema
    Cuando envío la petición para crear el usuario
    Entonces la respuesta debe tener el código de estado 409
    Y el mensaje de error debe indicar que el correo ya existe

  @Smoke
  Escenario: Consultar usuario existente por ID válido
    Cuando intento consultar el usuario con id "1"
    Entonces la respuesta debe tener el código de estado 200
    Y el correo del usuario debe estar presente

  @Critical
  Esquema del escenario: Crear usuario con diferentes perfiles de datos
    Dado que tengo los datos para un nuevo usuario llamado "<nombre>" con username "<username>" y correo "<correo>"
    Cuando envío la petición para crear el usuario
    Entonces la respuesta debe tener el código de estado 201
    Y la respuesta debe contener el id generado, el nombre "<nombre>", username "<username>" y correo "<correo>"

    Ejemplos:
      | nombre        | username      | correo                  |
      | Ana García    | ana.garcia    | ana.garcia@ejemplo.com  |
      | Carlos Ruiz   | carlos.ruiz   | carlos.ruiz@ejemplo.com |
      | María López   | maria.lopez   | maria.lopez@ejemplo.com |

  @Smoke
  Escenario: Obtener posts de un usuario existente
    Cuando consulto los posts del usuario con id "1"
    Entonces la respuesta debe tener el código de estado 200
    Y la lista de respuesta no debe estar vacía