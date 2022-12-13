package ru.lexical.state.impl.number.hex;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

import java.util.regex.Pattern;

public class HexState implements State {
    @Override
    public boolean is(char ch) {
        return Pattern.matches("[ABCDEFabcdef]", String.valueOf(ch));
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.HEX);
        while (reader.charsExists()
                && (Character.isDigit(reader.getCurrent())
                        || is(reader.getCurrent()))) {
            reader.add();
            reader.next();
        }
        return tryTransitToEnd(reader);
    }
    private Reader tryTransitToEnd(Reader reader){
        HexEndState hexEndState = new HexEndState();
        if (hexEndState.is(reader.getCurrent())) {
            reader = hexEndState.transit(reader);
        }
        else {
            reader.setStateType(StateType.ERR);
        }
        return reader;
    }
}
