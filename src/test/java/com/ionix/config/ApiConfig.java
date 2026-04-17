package com.ionix.config;

import io.restassured.builder.RequestSpecBuilder;
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
        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(new io.qameta.allure.restassured.AllureRestAssured()) // Reporte visual en Allure
                .addFilter(new RequestLoggingFilter())  // Log en consola de la petición
                .addFilter(new ResponseLoggingFilter()) // Log en consola de la respuesta
                .build();
    }
}