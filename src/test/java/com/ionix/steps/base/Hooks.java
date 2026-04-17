package com.ionix.steps.base;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup() {
        logger.info("--- Iniciando Escenario de Prueba ---");
        // No configuramos estado global (io.restassured.RestAssured.baseURI) se especifica en ApiConfig.
    }

    @After
    public void teardown() {
        logger.info("--- Escenario de Prueba Finalizado ---");
    }
}