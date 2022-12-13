package ru.lexical.state.impl.delimiter;

import ru.lexical.Reader;
import ru.lexical.file.FileLooker;
import ru.lexical.file.FileOuter;
import ru.lexical.file.TableUtil;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

public class BiggerCharState implements State {
    @Override
    public boolean is(char ch) {
        return ch == '>';
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.BIGGER_CHAR);
        if(reader.charsExists()){
            reader.next();
            if(reader.getCurrent()=='='){
                reader.add();
                new FileOuter().out(TableUtil.tlNumber,
                        new FileLooker(TableUtil.tlPath)
                                .look(reader.getBuffer()));
            }
            return reader;
        }
        reader.setStateType(StateType.ERR);
        return reader;
    }
}
