package ru.state;

public abstract class State {
    public abstract boolean is(String ch);
    public abstract StateData transit(StateData stateData);

}
