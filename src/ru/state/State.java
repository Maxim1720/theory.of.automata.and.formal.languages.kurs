package ru.state;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public abstract class State {
    public abstract boolean is(String ch);
    public abstract StateData transit(StateData stateData);

}
