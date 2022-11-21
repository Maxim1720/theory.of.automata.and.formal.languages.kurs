package ru.fsm.state.fabric;

import ru.fsm.state.current.IdentifierState;
import ru.fsm.state.State;

import java.util.ArrayList;
import java.util.List;

public class StatesFabric {
    public static List<State> currentStates(){
        ArrayList<State> states = new ArrayList<>();
        states.add(new IdentifierState());
        return states;
    }

    public static List<State> endStates(){
        ArrayList<State> states = new ArrayList<>();
        return states;
    }
}
