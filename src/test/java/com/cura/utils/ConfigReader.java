package com.cura.utils;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null) throw new RuntimeException("config.properties not found");
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load configuration", e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String value = System.getProperty(key);
        return value != null ? value : PROPERTIES.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
