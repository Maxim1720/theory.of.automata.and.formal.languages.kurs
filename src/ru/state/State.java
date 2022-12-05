package ru.state;

import ru.Reader;

public interface State {
    boolean is(char ch);
    Reader transit(Reader reader);

}
