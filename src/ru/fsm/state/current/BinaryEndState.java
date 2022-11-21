package ru.fsm.state.current;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;
import ru.fsm.state.end.ErrorState;
import ru.fsm.table.util.TableType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BinaryEndState extends State {

    private final List<State> nextStates;

    public BinaryEndState() {
        this.nextStates = new ArrayList<>();
        nextStates.add(new HexState());
        nextStates.add(new HexEndState());

        StringBuilder regex = new StringBuilder();
        for (State s: nextStates){
            regex.append(s.pattern().pattern());
        }
        nextStates.add(new ErrorState(Pattern.compile(regex.toString())));
    }

    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return pattern().matcher(String.valueOf(analyzeData.getCh())).matches();
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        for (State s: nextStates){
            if(s.isTransitive(analyzeData)){
                return s.transit(analyzeData);
            }
        }
        analyzeData.put(TableType.NUMBER);
        analyzeData.out(TableType.NUMBER);
        return analyzeData;
    }

    @Override
    public List<State> nextStates() {
        return nextStates;
    }

    @Override
    public Pattern pattern() {
        return Pattern.compile("[Bb]");
    }
}
