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

public final class Analyzer {

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
    public void analyze() {
        Reader reader = new Reader();
        reader.setStateType(StateType.START);
        if (reader.charsExists()) {
            while (!isNeedStopAnalyze(reader)) {
                reader = readLexem(reader);
                if (reader.getStateType().equals(StateType.ERR)) {
                    throw new IllegalArgumentException("invalid lexem: " + reader.getBuffer());
                }
            }
        }
    }

    private Reader readLexem(Reader reader){
        reader.flush();
        if (!Character.isWhitespace(reader.getCurrent())) {
            reader = tryTransit(reader);
            System.out.println(reader.getBuffer() + ": " + reader.getStateType());
        } else {
            reader.setStateType(StateType.WHITE_SPACE);
            reader.next();
        }
        return reader;
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
        return reader.getCurrent() == Character.MAX_VALUE;
    }
}