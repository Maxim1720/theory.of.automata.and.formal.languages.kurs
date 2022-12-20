package ru.exception.syn;

import ru.exception.ParseException;

public class SyntacticException extends ParseException {

    public SyntacticException(String lexeme, String expected) {
        super("Syntactic error near: '" + lexeme + "', expected: '" + expected + "'");
    }
}
