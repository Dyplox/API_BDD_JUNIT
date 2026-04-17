package com.ionix.context;

import io.restassured.response.Response;

/**
 * Contexto genérico para compartir estado transversal entre Steps en Cucumber.
 * Solo contiene estado agnóstico al dominio (la respuesta HTTP).
 * Para estado específico de un dominio, usar su propio contexto
 * (ej. UserContext, AuthContext).
 * PicoContainer garantiza una instancia por escenario (Scenario Scope).
 */
public class TestContext {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}