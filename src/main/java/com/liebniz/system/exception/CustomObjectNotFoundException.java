package com.liebniz.system.exception;

public class CustomObjectNotFoundException extends RuntimeException {
    public CustomObjectNotFoundException(String objectName, long id) {
        super("Could not find " + objectName + " with Id " + id);
    }
}
