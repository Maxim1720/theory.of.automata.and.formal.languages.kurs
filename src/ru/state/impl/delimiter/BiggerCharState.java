package ru.state.impl.delimiter;

import ru.Reader;
import ru.file.FileLooker;
import ru.file.FileOuter;
import ru.file.TableUtil;
import ru.state.State;
import ru.state.StateType;

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
