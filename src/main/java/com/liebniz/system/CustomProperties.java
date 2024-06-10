package com.liebniz.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CustomProperties extends Properties {

    public static CustomProperties loadProperties(String environment) {
        CustomProperties customProperties = new CustomProperties();
        try (InputStream input = CustomProperties.class.getClassLoader().getResourceAsStream("application-" + environment + ".properties")) {
            if (input == null) {
                throw new IllegalArgumentException("Environment properties file not found for: " + environment);
            }
            customProperties.load(input);
            customProperties.forEach((key, value) -> System.out.println(key + ", " + value));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return customProperties;
    }
}
