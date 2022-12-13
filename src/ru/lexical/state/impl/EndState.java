package ru.lexical.state.impl;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

public class EndState implements State {

    @Override
    public boolean is(char ch) {
        return ch == '}';
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.END);
        return reader;
    }
}
