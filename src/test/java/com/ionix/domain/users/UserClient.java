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
     * Extrae el email desde una respuesta ya obtenida (sin hacer una nueva petición HTTP).
     * Devuelve Optional.empty() si el estado no es 200 OK o si el campo email es nulo.
     */
    public Optional<String> extractEmail(Response response) {
        if (response.statusCode() == HttpStatus.OK.getCode()) {
            return Optional.ofNullable(response.jsonPath().getString("email"));
        }
        return Optional.empty();
    }

    @Step("Obtener posts del usuario con ID: {userId}")
    public Response getUserPosts(String userId) {
        return given()
                .spec(ApiConfig.getBaseSpec())
                .pathParam("id", userId)
                .when()
                .get(Endpoints.USER_POSTS);
    }
}