package ru.fsm.table;

import java.io.IOException;

public interface Putter {
    int put(String lex) throws IOException;
}
