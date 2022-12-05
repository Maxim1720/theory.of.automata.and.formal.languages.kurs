package ru.state.impl;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;

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
