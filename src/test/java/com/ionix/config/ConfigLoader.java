package com.ionix.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Carga las configuraciones desde archivos .properties basados en el entorno.
 */
public class ConfigLoader {

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
            System.out.println("Configuraciones cargadas exitosamente para el entorno: " + env.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Fallo al cargar el archivo de configuración: " + propertyFileName, e);
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
            throw new IllegalArgumentException("La propiedad '" + key + "' no está definida.");
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