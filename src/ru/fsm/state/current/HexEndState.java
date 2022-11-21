package ru.fsm.state.current;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HexEndState extends State {

    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return Pattern.matches("[Hh]",String.valueOf(analyzeData.getCh()));
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        return analyzeData;
    }

    @Override
    public List<State> nextStates() {
        return new ArrayList<>();
    }

    @Override
    public Pattern pattern() {
        return null;
    }
}
