package ru;

import ru.state.State;
import ru.state.StateType;
import ru.state.impl.CommentState;
import ru.state.impl.IdentifierState;
import ru.state.impl.delimiter.DelimiterState;
import ru.state.impl.number.FloatState;
import ru.state.impl.number.binary.BinaryState;
import ru.state.impl.number.decimal.DecimalState;
import ru.state.impl.number.oct.OctState;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    private final List<State> states;

    public Analyzer() {

        states = new ArrayList<>();
        states.add(new IdentifierState());
        states.add(new BinaryState());
        states.add(new OctState());
        states.add(new DecimalState());
        states.add(new FloatState());
        states.add(new CommentState());
        states.add(new DelimiterState());

    }




    //если символ был проигнорирован состоянием, его следует сохранить до следующего чтения
    public void analyze() {
        Reader reader = new Reader();
        reader.setStateType(StateType.START);
        if (reader.charsExists()) {
            while (!isNeedStopAnalyze(reader)) {
                if (reader.getCurrent() == Character.MIN_VALUE) {
                    reader.next();
                }
                reader.flush();
                if (!Character.isWhitespace(reader.getCurrent())) {
                    reader = tryTransit(reader);
                    System.out.println(reader.getBuffer() + ": " + reader.getStateType());
                } else {
                    reader.setStateType(StateType.WHITE_SPACE);
                    reader.next();
                }
                if (reader.getStateType().equals(StateType.ERR)) {
                    throw new IllegalArgumentException("invalid lexem: " + reader.getBuffer());
                }

            }
        }

    }

    private Reader tryTransit(Reader reader) {
        for (State s : states) {
            if (s.is(reader.getCurrent())) {
                return s.transit(reader);
            }
        }
        return reader;
    }

    private boolean isNeedStopAnalyze(Reader reader) {
        return !reader.charsExists()
                || reader.getStateType().equals(StateType.END);
    }
}