package ru.exception.lex;

import ru.exception.ParseException;

public class IncorrectLexemeException extends ParseException {
    private final String lexeme;
    public IncorrectLexemeException(String lexeme){
        super("Incorrect lexeme: " + lexeme);
        this.lexeme = lexeme;
    }
    public String getLexeme() {
        return lexeme;
    }
}
