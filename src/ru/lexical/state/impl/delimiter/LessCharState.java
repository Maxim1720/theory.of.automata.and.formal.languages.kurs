package ru.lexical.state.impl.delimiter;

import ru.lexical.Reader;
import ru.lexical.file.FileLooker;
import ru.lexical.file.FileOuter;
import ru.util.TableUtil;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

public class LessCharState implements State {
    @Override
    public boolean is(char ch) {
        return ch == '<';
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.LESS_CHAR);
        if(reader.charsExists()){
            reader.next();
            char c = reader.getCurrent();
            if(c == '>' || c == '='){
                reader.add();
                new FileOuter().out(TableUtil.tlNumber,
                        new FileLooker(TableUtil.tlPath).look(reader.getBuffer()));
                reader.next();
            }
            else {
                new FileOuter().out(TableUtil.tlNumber,
                        new FileLooker(TableUtil.tlPath).look(reader.getBuffer()));
            }
            return reader;
        }
        reader.setStateType(StateType.ERR);
        return reader;
    }
}
