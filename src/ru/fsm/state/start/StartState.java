package ru.fsm.state.start;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;

import java.util.List;
import java.util.regex.Pattern;

public class StartState extends State {
    private final List<State> states;
    public StartState(List<State> states){
        this.states = states;
    }
    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return true;
    }
    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        analyzeData.flush();
        analyzeData.gc();
        int i=-1;
        for (State s : states){
            if(s.isTransitive(analyzeData)){
                i = states.indexOf(s);
            }
        }
        if(i>=0){
            return states.get(i).transit(analyzeData);
        }
        return analyzeData;
    }

    @Override
    public List<State> nextStates() {
        return states;
    }

    @Override
    public Pattern pattern() {
        return null;
    }
}
