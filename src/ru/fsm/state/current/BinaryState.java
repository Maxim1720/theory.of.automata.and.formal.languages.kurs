package ru.fsm.state.current;

import ru.fsm.AnalyzeData;
import ru.fsm.StateType;
import ru.fsm.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BinaryState extends State {

    private final List<State> nextStates;

    public BinaryState() {
        this.nextStates = new ArrayList<>();
        nextStates.add(new OctState());
        nextStates.add(new DecimalState());
        nextStates.add(new HexState());
        nextStates.add(new FloatState());
        nextStates.add(new EpsState());
    }

    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return pattern().matcher(String.valueOf(analyzeData.getCh())).matches();
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        analyzeData.setStateType(StateType.BINARY);
        while (isTransitive(analyzeData)){
            analyzeData.gc();
        }
        if (isEnd(analyzeData.getCh())){
            return analyzeData;
        } else if (isError(analyzeData.getCh())){
            analyzeData.setStateType(StateType.ERR);
            return analyzeData;
        }

        State state = null;
        for (State s : nextStates){
            if(s.isTransitive(analyzeData)){
                state = s;
            }
        }
        if(state == null){
            analyzeData.gc();
        }

        return state.transit(analyzeData);
    }

    @Override
    public List<State> nextStates() {
        return nextStates;
    }

    @Override
    public Pattern pattern() {
        return Pattern.compile("[01]");
    }

    private boolean isError(char ch){
        return !Pattern.matches("[ABCDFabcdf0-9Oo.Hh]", String.valueOf(ch));
    }

    private boolean isEnd(char ch){
        return Pattern.matches("[Bb]", String.valueOf(ch));
    }
}
