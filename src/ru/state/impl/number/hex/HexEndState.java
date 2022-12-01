package ru.state.impl.number.hex;

import ru.Reader;
import ru.handler.NumberLexemHandler;
import ru.state.State;
import ru.state.StateType;

import java.util.regex.Pattern;

public class HexEndState extends State {

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
