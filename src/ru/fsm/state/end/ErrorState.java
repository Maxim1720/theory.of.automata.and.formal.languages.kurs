package ru.fsm.state.end;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;

import java.util.List;
import java.util.regex.Pattern;

public class ErrorState extends State {

    private final Pattern pattern;

    public ErrorState(Pattern antiPattern){
        this.pattern = antiPattern;
    }

    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return !pattern.matcher(String.valueOf(analyzeData.getCh())).matches();
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        throw new IllegalArgumentException("incorrect lexem");
    }

    @Override
    public List<State> nextStates() {
        return null;
    }

    @Override
    public Pattern pattern() {
        return pattern;
    }
}
