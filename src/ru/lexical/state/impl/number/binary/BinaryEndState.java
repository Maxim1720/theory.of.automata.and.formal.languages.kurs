package ru.lexical.state.impl.number.binary;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.number.hex.HexEndState;
import ru.lexical.state.impl.number.hex.HexState;

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
        reader.setStateType(StateType.BINARY_END);
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
        return reader;
    }
}
