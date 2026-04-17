package com.ionix.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Encargado de construir las especificaciones globales de RestAssured,
 * inyectando de forma dinámica los valores del entorno.
 */
public class ApiConfig {

    public static RequestSpecification getBaseSpec() {
        int timeout = ConfigLoader.getTimeout();

        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", timeout) // Tiempo máximo para establecer conexión TCP
                                .setParam("http.socket.timeout", timeout)))   // Tiempo máximo esperando datos del servidor
                .addFilter(new io.qameta.allure.restassured.AllureRestAssured()) // Reporte visual en Allure
                .addFilter(new RequestLoggingFilter())  // Log en consola de la petición
                .addFilter(new ResponseLoggingFilter()) // Log en consola de la respuesta
                .build();
    }
}