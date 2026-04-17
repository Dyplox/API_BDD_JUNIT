package com.ionix.context;

import com.ionix.domain.users.models.CreateUserRequest;
import io.restassured.response.Response;

import java.util.Optional;

/**
 * Clase de contexto para compartir estado entre diferentes clases de Steps en Cucumber.
 * PicoContainer se encarga de inyectar la misma instancia de esta clase 
 * durante la ejecución de un mismo escenario (Scenario Scope).
 */
public class TestContext {

    private CreateUserRequest request;
    private Response response;
    private Optional<String> searchedEmail = Optional.empty();

    public CreateUserRequest getRequest() {
        return request;
    }

    public void setRequest(CreateUserRequest request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Optional<String> getSearchedEmail() {
        return searchedEmail;
    }

    public void setSearchedEmail(Optional<String> searchedEmail) {
        this.searchedEmail = searchedEmail;
    }
}