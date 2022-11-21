package ru.newFsm;

import ru.fsm.FSM;
import ru.fsm.StateType;
import ru.fsm.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FiniteStatesMachine implements FSM {

    private StateType currentStateType;
    private char ch;

    private ru.newFsm.state.State currentState;

    @Override
    public StateType state() {
        return currentStateType;
    }

    @Override
    public void putChar(char c) {
        if(Pattern.compile("[\\s]").matcher(String.valueOf(c)).matches()){
            return;
        }

    }

    public List<State> states(){
        return new ArrayList<>();
    }
}
