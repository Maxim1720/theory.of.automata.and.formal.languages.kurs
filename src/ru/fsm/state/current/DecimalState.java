package ru.fsm.state.current;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;

import java.util.List;
import java.util.regex.Pattern;

public class DecimalState extends State {
    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return false;
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        return null;
    }

    @Override
    public List<State> nextStates() {
        return null;
    }

    @Override
    public Pattern pattern() {
        return null;
    }
}
