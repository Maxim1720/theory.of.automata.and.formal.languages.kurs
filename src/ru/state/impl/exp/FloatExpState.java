package ru.state.impl.exp;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;

import java.util.regex.Pattern;

public class FloatExpState extends State {

    @Override
    public boolean is(char ch) {
        return Pattern.compile("[Ee]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.FLOAT_EXP);
        reader.add();
        reader.next();
        if(reader.charsExists()){
            if(reader.getCurrent()=='+' || reader.getCurrent()=='-'){
                reader.add();
                reader.next();
                addAllNextDigits(reader);
            } else if (Character.isDigit(reader.getCurrent())) {
                reader.add();
                addAllNextDigits(reader);
            }
            else {
                reader.setStateType(StateType.ERR);
            }
        }
        return reader;
    }
    private Reader addAllNextDigits(Reader reader){
        while (reader.charsExists() && Character.isDigit(reader.getCurrent())){
            reader.add();
            reader.next();
        }
        return reader;
    }
}
