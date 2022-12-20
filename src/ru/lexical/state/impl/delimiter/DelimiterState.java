package ru.lexical.state.impl.delimiter;

import ru.lexical.Reader;
import ru.lexical.file.FileLooker;
import ru.lexical.file.FileOuter;
import ru.util.TableUtil;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.EndState;

import java.util.HashSet;
import java.util.Set;

public class DelimiterState implements State {

    private final Set<State> states;

    public DelimiterState(){
        states = new HashSet<>();
        states.add(new LessCharState());
        states.add(new BiggerCharState());
    }

    @Override
    public boolean is(char ch) {
        return new FileLooker(TableUtil.tlPath).look(String.valueOf(ch))!=0;
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.DELIMITER);
        reader.add();

        for (State s : states){
            if(s.is(reader.getCurrent())){
                return s.transit(reader);
            }
        }
        int z = new FileLooker(TableUtil.tlPath).look(reader.getBuffer());
        if(z!=0){
            new FileOuter().out(TableUtil.tlNumber, z);
            if(new EndState().is(reader.getCurrent())){
                reader = new EndState().transit(reader);
            }
        }
        else {
            reader.setStateType(StateType.ERR);
        }
        reader.next();
        return reader;
    }
}
