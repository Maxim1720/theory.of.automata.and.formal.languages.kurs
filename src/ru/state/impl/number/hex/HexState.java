package ru.state.impl.number.hex;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;

import java.util.regex.Pattern;

public class HexState extends State {
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