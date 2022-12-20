package ru.lexical.state.impl;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

public class CommentState implements State {
    @Override
    public boolean is(char ch) {
        return ch == '#';
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.COMMENT);
        boolean end = false;
        while (reader.charsExists() && !end){
            reader.next();
            if(reader.getCurrent() == '#'){
                end = true;
                reader.next();
            }
        }
        if(!end){
            reader.add();
            reader.setStateType(StateType.ERR);
        }
        return reader;
    }
}
