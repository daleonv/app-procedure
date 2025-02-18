package com.ec.app.microservices.resources;

import java.util.MissingResourceException;

public class ProcedureProperties {

    public static final ProcedureResourceResolver MESSAGE_RESOLVER =
            new ProcedureResourceResolver("com.ec.app.microservices.resources.procedure");

    private ProcedureProperties() {
    }

    /**
     * Get string
     *
     * @param key Property key
     * @return Property value
     */
    public static String getString(String key) {
        try {
            return MESSAGE_RESOLVER.getString(key);
        } catch (MissingResourceException e) {
            throw new RuntimeException("Clave no encontrada en el ResourceBundle: " + key, e);
        }
    }

}
