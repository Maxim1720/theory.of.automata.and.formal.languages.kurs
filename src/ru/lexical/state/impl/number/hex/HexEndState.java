package ru.lexical.state.impl.number.hex;

import ru.lexical.Reader;
import ru.lexical.handler.NumberLexemHandler;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

import java.util.regex.Pattern;

public class HexEndState implements State {

    @Override
    public boolean is(char ch) {
        return Pattern.compile("[Hh]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.HEX_END);
        reader.add();
        reader.next();
        if(!Character.isWhitespace(reader.getCurrent())
                && !reader.currentIsDelimiter()){
            reader.add();
            reader.setStateType(StateType.ERR);
        }

        new NumberLexemHandler().handle(reader.getBuffer());
        return reader;
    }
}
