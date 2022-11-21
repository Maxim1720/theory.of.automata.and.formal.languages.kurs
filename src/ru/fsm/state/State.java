package ru.fsm.state;

import ru.fsm.AnalyzeData;

import java.util.List;
import java.util.regex.Pattern;

public abstract class State {
    public abstract boolean isTransitive(AnalyzeData analyzeData);
    public abstract AnalyzeData transit(AnalyzeData analyzeData);

    public abstract List<State> nextStates();

    public abstract Pattern pattern();
}
