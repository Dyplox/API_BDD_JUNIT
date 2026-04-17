package com.ionix.domain.users;

import com.ionix.config.ApiConfig;
import com.ionix.domain.users.models.CreateUserRequest;
import com.ionix.utils.Endpoints;
import com.ionix.utils.HttpStatus;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Optional;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Cliente de Dominio para la gestión de Usuarios.
 * Encapsula la lógica de peticiones HTTP y exposición de pasos para Allure.
 */
public class UserClient {

    @Step("Crear un nuevo usuario con los datos provistos")
    public Response createUser(CreateUserRequest request) {
        Response response = given()
                .spec(ApiConfig.getBaseSpec())
                .body(request)
                .when()
                .post(Endpoints.USERS);

        // Validación de contrato de la respuesta exitosa (código 201 Created)
        if (response.statusCode() == HttpStatus.CREATED.getCode()) {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user_creation_schema.json"));
        }
        
        return response;
    }

    @Step("Consultar usuario con ID: {userId}")
    public Response getUser(String userId) {
        Response response = given()
                .spec(ApiConfig.getBaseSpec())
                .pathParam("id", userId)
                .when()
                .get(Endpoints.USER_BY_ID);

        // Validación de contrato de la respuesta exitosa
        if (response.statusCode() == HttpStatus.OK.getCode()) {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user_schema.json"));
        }

        return response;
    }

    /**
     * Devuelve un Optional<String> con el email si existe y el estado es 200 (OK).
     * Si no existe o hay error, devuelve Optional.empty() en lugar de un String mágico.
     */
    @Step("Obtener email de forma segura para el usuario: {userId}")
    public Optional<String> getUserEmail(String userId) {
        Response response = getUser(userId);

        if (response.statusCode() == HttpStatus.OK.getCode()) {
            String email = response.jsonPath().getString("email");
            return Optional.ofNullable(email);
        }
        
        // En lugar de devolver "USUARIO_NO_ENCONTRADO", devolvemos vacío (Empty)
        // Esto obliga a quien llame la función a manejar la ausencia del dato correctamente.
        return Optional.empty();
    }
}