package ru.lexical;

import ru.Analyzer;
import ru.exception.lex.IncorrectLexemeException;
import ru.lexical.file.LexemeOuter;
import ru.lexical.handler.NumberLexemHandler;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.CommentState;
import ru.lexical.state.impl.IdentifierState;
import ru.lexical.state.impl.delimiter.DelimiterState;
import ru.lexical.state.impl.number.FloatState;
import ru.lexical.state.impl.number.binary.BinaryState;
import ru.lexical.state.impl.number.decimal.DecimalState;
import ru.lexical.state.impl.number.oct.OctState;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer implements Analyzer {

    private final List<State> states;

    public LexicalAnalyzer() {
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
                reader = readLexeme(reader);
                if (reader.getStateType().equals(StateType.ERR)) {
                    throw new IncorrectLexemeException(reader.getBuffer());
                }
                if(!reader.getBuffer().equals("")) {
                    new LexemeOuter().out(reader.getBuffer());
                    if(isNumber(reader)){
                        new NumberLexemHandler().handle(reader.getBuffer());
                    }
                }
            }
        }
    }

    private Reader readLexeme(Reader reader){
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

    private boolean isNumber(Reader reader){
        StateType st = reader.getStateType();
        return (st.equals(StateType.FLOAT)
                || st.equals(StateType.DEC)
                || st.equals(StateType.BINARY)
                || st.equals(StateType.EXP)
                || st.equals(StateType.DEC_END)
                || st.equals(StateType.FLOAT_EXP)
                || st.equals(StateType.OCT_END)
                || st.equals(StateType.OCT)
                || st.equals(StateType.BINARY_END)
                || st.equals(StateType.HEX)
                || st.equals(StateType.HEX_END));
    }
}