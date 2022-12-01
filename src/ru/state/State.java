package ru.state;

import ru.Reader;

public abstract class State {
    public abstract boolean is(char ch);
    public abstract Reader transit(Reader reader);

}
