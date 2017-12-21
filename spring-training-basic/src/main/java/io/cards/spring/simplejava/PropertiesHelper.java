package io.cards.spring.simplejava;

import java.util.ResourceBundle;

public class PropertiesHelper {
    public static String getProperty(String file, String property) {
        return ResourceBundle.getBundle(file).getString(property);
    }
}
