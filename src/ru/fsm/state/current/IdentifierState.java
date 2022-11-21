package ru.fsm.state.current;

import ru.fsm.AnalyzeData;
import ru.fsm.state.State;
import ru.fsm.state.start.StartState;
import ru.fsm.table.util.TableType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class IdentifierState extends State {
    @Override
    public boolean isTransitive(AnalyzeData analyzeData) {
        return analyzeData.let() && analyzeData.canRead();
    }

    @Override
    public AnalyzeData transit(AnalyzeData analyzeData) {
        analyzeData.flush();
        analyzeData.add();
        analyzeData.gc();

        while ((analyzeData.let() || analyzeData.digit())){
            analyzeData.add();
            analyzeData.gc();
        }

        if(analyzeData.look(TableType.KEY_WORD)!=0){
            analyzeData.out(TableType.KEY_WORD);
        }
        else {
            if(analyzeData.look(TableType.DELIMITER)!=0){
                analyzeData.out(TableType.DELIMITER);
            }
            else {
                analyzeData.put(TableType.IDENTIFIER);
                analyzeData.out(TableType.IDENTIFIER);
            }
        }
        return analyzeData;
    }

    @Override
    public List<State> nextStates() {
        return new ArrayList<>();
    }

    @Override
    public Pattern pattern() {
        return Pattern.compile("[a-zA-Z]");
    }
}
