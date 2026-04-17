package com.ionix.steps.users;

import com.ionix.context.TestContext;
import com.ionix.context.UserContext;
import com.ionix.domain.users.models.CreateUserRequest;
import com.ionix.domain.users.UserClient;
import com.ionix.domain.users.models.UserResponse;
import com.ionix.utils.ErrorMessages;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.response.Response;
import net.datafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {

    private final TestContext context;
    private final UserContext userContext;
    private final UserClient userClient;
    private final Faker faker;

    // PicoContainer inyecta automáticamente todos los colaboradores:
    // TestContext (genérico), UserContext (dominio usuario),
    // UserClient (cliente HTTP) y Faker (generador de datos).
    public UserSteps(TestContext context, UserContext userContext, UserClient userClient, Faker faker) {
        this.context = context;
        this.userContext = userContext;
        this.userClient = userClient;
        this.faker = faker;
    }

    @Dado("que tengo los datos para un nuevo usuario llamado {string} con username {string} y correo {string}")
    public void iHaveDataForNewUser(String nombre, String username, String correo) {
        userContext.setRequest(new CreateUserRequest(nombre, username, correo));
    }

    @Dado("que genero datos aleatorios para un nuevo usuario")
    public void iGenerateRandomDataForNewUser() {
        String randomName = faker.name().fullName();
        String randomUsername = faker.internet().username();
        String randomEmail = faker.internet().emailAddress();

        userContext.setRequest(new CreateUserRequest(randomName, randomUsername, randomEmail));
    }

    @Y("el usuario ya se encuentra registrado previamente en el sistema")
    public void theUserIsAlreadyRegisteredInSystem() {
        // En un caso real, esto puede ser una llamada a base de datos o a la API de creación previa.
        // Aquí simulamos que enviamos la creación para que ya exista, de modo que la siguiente llamada devuelva error.
        userClient.createUser(userContext.getRequest());
    }

    @Cuando("envío la petición para crear el usuario")
    public void iSendRequestToCreateUser() {
        context.setResponse(userClient.createUser(userContext.getRequest()));
    }

    @Entonces("la respuesta debe tener el código de estado {int}")
    public void responseShouldHaveStatusCode(int statusCode) {
        assertThat(context.getResponse().statusCode())
                .as("El código de estado HTTP no coincide con el esperado")
                .isEqualTo(statusCode);
    }

    @Y("la respuesta debe contener el id generado y los datos enviados coinciden")
    public void responseShouldContainGeneratedIdAndMatchData() {
        UserResponse userResponse = context.getResponse().as(UserResponse.class);
        CreateUserRequest expectedRequest = userContext.getRequest();

        assertUserHasValidId(userResponse);
        assertUserDataMatches(userResponse, expectedRequest.name(), expectedRequest.username(), expectedRequest.email());
    }

    @Y("la respuesta debe contener el id generado, el nombre {string}, username {string} y correo {string}")
    public void responseShouldContainUserData(String nombre, String username, String correo) {
        UserResponse userResponse = context.getResponse().as(UserResponse.class);

        assertUserHasValidId(userResponse);
        assertUserDataMatches(userResponse, nombre, username, correo);
    }

    private void assertUserHasValidId(UserResponse userResponse) {
        assertThat(userResponse.id())
                .as("El ID del usuario debería ser mayor a 0")
                .isNotNull()
                .isGreaterThan(0);
    }

    private void assertUserDataMatches(UserResponse userResponse, String expectedName, String expectedUsername, String expectedEmail) {
        assertThat(userResponse.name())
                .as("El nombre del usuario no coincide")
                .isEqualTo(expectedName);
        assertThat(userResponse.username())
                .as("El username no coincide")
                .isEqualTo(expectedUsername);
        assertThat(userResponse.email())
                .as("El correo del usuario no coincide")
                .isEqualTo(expectedEmail);
    }

    @Y("el mensaje de error debe indicar que el correo ya existe")
    public void errorMessageShouldIndicateEmailExists() {
        String actualMessage = context.getResponse().jsonPath().getString("message");

        assertThat(actualMessage)
                .as("El mensaje de error de la API no es el esperado")
                .isEqualTo(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
    }

    @Cuando("intento consultar el usuario con id {string}")
    public void iTryToFetchUserById(String id) {
        // Una sola petición HTTP: guardamos la respuesta y extraemos el email de ella.
        Response response = userClient.getUser(id);
        context.setResponse(response);
        userContext.setSearchedEmail(userClient.extractEmail(response));
    }

    @Entonces("el correo del usuario debe ser vacío")
    public void userEmailShouldBeEmpty() {
        assertThat(userContext.getSearchedEmail())
                .as("El email no debería estar presente para un usuario inexistente")
                .isEmpty();
    }

    @Entonces("el correo del usuario debe estar presente")
    public void userEmailShouldBePresent() {
        assertThat(userContext.getSearchedEmail())
                .as("El email debería estar presente para un usuario existente")
                .isPresent();
    }

    @Cuando("consulto los posts del usuario con id {string}")
    public void iGetUserPosts(String id) {
        context.setResponse(userClient.getUserPosts(id));
    }

    @Entonces("la lista de respuesta no debe estar vacía")
    public void responseListShouldNotBeEmpty() {
        int size = context.getResponse().jsonPath().getList("$").size();
        assertThat(size)
                .as("La lista de la respuesta no debería estar vacía")
                .isGreaterThan(0);
    }
}