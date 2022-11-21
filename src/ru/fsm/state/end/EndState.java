package ru.fsm.state.end;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;

import java.util.List;
import java.util.regex.Pattern;

public class EndState extends State {

    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return pattern().matcher(String.valueOf(analyzeData.getCh())).matches();
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        return analyzeData;
    }

    @Override
    public List<State> nextStates() {
        return null;
    }

    @Override
    public Pattern pattern() {
        return Pattern.compile("}");
    }
}
