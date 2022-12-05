package ru.state.impl.number;

import ru.Reader;
import ru.handler.NumberLexemHandler;
import ru.state.State;
import ru.state.StateType;
import ru.state.impl.exp.FloatExpState;

import java.util.regex.Pattern;

public class FloatState implements State {
    @Override
    public boolean is(char ch) {
        return Pattern.compile("[.]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.FLOAT);
        reader.add();
        reader.next();
        boolean existsDigits = false;

        while (reader.charsExists() && Character.isDigit(reader.getCurrent())){
            reader.add();
            reader.next();
            existsDigits = true;
        }
        if (existsDigits){
            FloatExpState floatExpState = new FloatExpState();
            if(floatExpState.is(reader.getCurrent())){
                reader = floatExpState.transit(reader);
            }
            else if((!Character.isWhitespace(reader.getCurrent())
                    && !reader.currentIsDelimiter())) {
                reader.add();
                reader.setStateType(StateType.ERR);
            }
        }
        else{
            reader.add();
            reader.setStateType(StateType.ERR);
        }
        new NumberLexemHandler().handle(reader.getBuffer());
        return reader;
    }
}
