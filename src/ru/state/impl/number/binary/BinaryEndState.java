package ru.state.impl.number.binary;

import ru.Reader;
import ru.handler.NumberLexemHandler;
import ru.state.State;
import ru.state.StateType;
import ru.state.impl.number.hex.HexEndState;
import ru.state.impl.number.hex.HexState;

import java.util.regex.Pattern;

public class BinaryEndState implements State {

    private final HexState hexState;
    private final HexEndState hexEndState;

    public BinaryEndState() {
        hexEndState = new HexEndState();
        hexState = new HexState();
    }


    @Override
    public boolean is(char ch) {
        return Pattern.compile("[Bb]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.add();
        reader.next();
        if(hexState.is(reader.getCurrent())
                || Character.isDigit(reader.getCurrent())){
            reader = hexState.transit(reader);
        } else if (hexEndState.is(reader.getCurrent())) {
            reader = hexEndState.transit(reader);
        } else if (!Character.isWhitespace(reader.getCurrent())
                && !reader.currentIsDelimiter()) {
            reader.setStateType(StateType.ERR);
        }
        else{
            new NumberLexemHandler().handle(reader.getBuffer());
        }
        return reader;
    }
}
