package com.ionix.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Carga las configuraciones desde archivos .properties basados en el entorno.
 */
public class ConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static final Properties properties = new Properties();

    static {
        // Lee la variable del sistema 'env'. Si no se pasa, usa 'qa' por defecto.
        String env = System.getProperty("env", "qa");
        String propertyFileName = "config-" + env + ".properties";

        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (inputStream == null) {
                throw new RuntimeException("No se encontró el archivo de propiedades: " + propertyFileName);
            }
            properties.load(inputStream);
            logger.info("✔ Configuración cargada — entorno: [{}] — archivo: [{}]", env.toUpperCase(), propertyFileName);
        } catch (IOException e) {
            throw new RuntimeException("Fallo al leer el archivo de configuración: " + propertyFileName, e);
        }
    }

    /**
     * Obtiene el valor de una propiedad.
     * @param key La clave de la propiedad (ej. "api.base.url")
     * @return El valor correspondiente en el archivo .properties.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("La propiedad '" + key + "' no está definida en el archivo de configuración.");
        }
        return value;
    }

    // Métodos de conveniencia para accesos tipados
    public static String getBaseUrl() {
        return getProperty("api.base.url");
    }

    public static int getTimeout() {
        return Integer.parseInt(getProperty("api.timeout"));
    }
}