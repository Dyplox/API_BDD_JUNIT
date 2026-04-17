package com.ionix.utils;

/**
 * Centraliza los mensajes de error esperados de la API o del sistema,
 * facilitando las aserciones y el mantenimiento si cambian los textos del backend.
 */
public enum ErrorMessages {

    USER_NOT_FOUND("El usuario no fue encontrado."),
    INVALID_CREDENTIALS("Credenciales inválidas."),
    EMAIL_ALREADY_EXISTS("El correo electrónico ya se encuentra registrado."),
    INTERNAL_SERVER_ERROR("Error interno del servidor.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}