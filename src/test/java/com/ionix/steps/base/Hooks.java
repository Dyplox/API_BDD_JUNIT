package com.ionix.steps.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup(Scenario scenario) {
        logger.info("================================================================");
        logger.info("▶ INICIANDO : {}", scenario.getName());
        logger.info("   Tags     : {}", scenario.getSourceTagNames());
        logger.info("================================================================");
    }

    @After
    public void teardown(Scenario scenario) {
        logger.info("================================================================");
        logger.info("■ FINALIZADO: {} — [{}]", scenario.getName(), scenario.getStatus());
        logger.info("================================================================");
    }
}