package com.ionix.context;

import com.ionix.domain.users.models.CreateUserRequest;
import java.util.Optional;

/**
 * Contexto específico del dominio de Usuarios.
 * Almacena el estado propio de los escenarios de usuario entre Steps.
 * PicoContainer gestiona su ciclo de vida por escenario (Scenario Scope),
 * garantizando aislamiento entre escenarios sin configuración adicional.
 */
public class UserContext {

    private CreateUserRequest request;
    private Optional<String> searchedEmail = Optional.empty();

    public CreateUserRequest getRequest() {
        return request;
    }

    public void setRequest(CreateUserRequest request) {
        this.request = request;
    }

    public Optional<String> getSearchedEmail() {
        return searchedEmail;
    }

    public void setSearchedEmail(Optional<String> searchedEmail) {
        this.searchedEmail = searchedEmail;
    }
}
