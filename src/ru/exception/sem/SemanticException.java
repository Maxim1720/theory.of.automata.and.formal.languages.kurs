package ru.exception.sem;

import ru.exception.ParseException;

public class SemanticException extends ParseException {
    public SemanticException(String message) {
        super("Incorrect semantic definition: " + message);
    }
}
