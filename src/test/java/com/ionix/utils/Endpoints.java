package com.ionix.utils;

/**
 * Centraliza todos los endpoints de la API para mantener consistencia 
 * y facilitar las actualizaciones si las rutas cambian.
 */
public final class Endpoints {

    // Prevenir instanciación
    private Endpoints() {
        throw new IllegalStateException("Utility class");
    }

    // --- MÓDULO DE USUARIOS ---
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{id}";
    public static final String USER_POSTS = "/users/{id}/posts";

    // --- MÓDULO DE AUTENTICACIÓN ---
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_REGISTER = "/auth/register";

    // --- MÓDULO DE POSTS ---
    public static final String POSTS = "/posts";
    public static final String POST_BY_ID = "/posts/{id}";
}