package ru.newFsm.state;

import ru.fsm.AnalyzeData;
import ru.fsm.StateType;

import java.util.List;
import java.util.regex.Pattern;

public abstract class State {

    public abstract StateType state();
    public abstract boolean isTransitive(char c);
    private String buffer;

    public StateType transit(char c){
        while (read(c));
        State s = null;
        for (State st: nextStates()){
            if(st.isTransitive(c)){
                s = st;
            }
        }
        return s.transit(c);
    }

    public abstract List<State> nextStates();
    public abstract Pattern pattern();

    public boolean read(char c){
        if(pattern().matcher(String.valueOf(c)).matches()){
            gc();
            return true;
        }
        return false;
    }

    protected abstract void gc();
    protected abstract void look();
    protected abstract void put();
    protected abstract void out();
}
