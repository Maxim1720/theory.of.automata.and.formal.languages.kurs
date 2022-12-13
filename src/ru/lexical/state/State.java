package ru.lexical.state;

import ru.lexical.Reader;

public interface State {
    boolean is(char ch);
    Reader transit(Reader reader);

}
