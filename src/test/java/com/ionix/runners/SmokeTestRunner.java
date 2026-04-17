package com.ionix.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner de SMOKE: ejecuta solo los escenarios etiquetados con @Smoke o @Critical.
 * Ideal para pipelines de CI/CD donde se necesita una validación rápida
 * antes del despliegue (feedback en segundos, no en minutos).
 *
 * Ejecución:
 *   ./gradlew test -PtestRunner=SmokeTestRunner
 * o directamente desde IntelliJ haciendo Run sobre esta clase.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ionix.steps")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@Smoke or @Critical")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class SmokeTestRunner {
}
