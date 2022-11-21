package ru.fsm;

import ru.fsm.state.start.StartState;
import ru.fsm.state.State;
import ru.fsm.state.fabric.StatesFabric;

import java.util.ArrayList;

public class FiniteStatesMachine {
    private final State startState;
    private final ArrayList<State> currentStates;
    private final ArrayList<State> endStates;

    private AnalyzeData analyzeData;

    public FiniteStatesMachine(String code){
        currentStates = new ArrayList<>(StatesFabric.currentStates());
        startState = new StartState(currentStates);
        endStates = new ArrayList<>(StatesFabric.endStates());

        analyzeData = new AnalyzeData(code);
    }

    public void run(){
        while (analyzeData.canRead()){
            analyzeData = startState.transit(analyzeData);
        }
    }




}
