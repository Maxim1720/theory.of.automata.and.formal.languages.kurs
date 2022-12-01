package ru.state.impl;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;

public class CommentState extends State {
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
            reader.setStateType(StateType.ERR);
            //throw new IllegalArgumentException("comment wasn't end");
        }
        return reader;
    }
}
