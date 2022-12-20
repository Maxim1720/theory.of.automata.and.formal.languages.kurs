package ru.lexical.state.impl.number.oct;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

import java.util.regex.Pattern;

public class OctEndState implements State {
    @Override
    public boolean is(char ch) {
        return Pattern.compile("[Oo]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.OCT_END);
        reader.add();
        if(reader.charsExists()) {
            reader.next();
            if (!Character.isWhitespace(reader.getCurrent())
                    && !reader.currentIsDelimiter()) {
                reader.add();
                reader.setStateType(StateType.ERR);
            }
        }
        return reader;
    }
}
