package ru.syntactical.reader;

public interface LexemeReader {
    boolean next();
    boolean eq(String s);
    boolean identifier();
    boolean number();

    String current();
}
