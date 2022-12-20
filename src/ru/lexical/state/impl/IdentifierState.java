package ru.lexical.state.impl;

import ru.lexical.Reader;
import ru.lexical.file.FileLooker;
import ru.lexical.file.FileOuter;
import ru.lexical.file.FilePutter;
import ru.util.TableUtil;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

public class IdentifierState implements State {

    @Override
    public boolean is(char ch) {
        return Character.isLetter(ch);
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.IDENTIFIER);
        while (reader.charsExists()
                && (is(reader.getCurrent())
                || Character.isDigit(reader.getCurrent()))){
            reader.add();
            reader.next();
        }
        writeToFiles(reader);
        return reader;
    }

    private void writeToFiles(Reader reader){
        int z = new FileLooker(TableUtil.twPath).look(reader.getBuffer());
        if(z!=0){
            reader.setStateType(StateType.KEY_WORD);
            new FileOuter().out(TableUtil.twNumber,z);
        }
        else{
            reader.setStateType(StateType.DELIMITER);
            z = new FileLooker(TableUtil.tlPath).look(reader.getBuffer());
            if(z!=0){
                new FileOuter().out(TableUtil.tlNumber,z);
            } else {
                reader.setStateType(StateType.IDENTIFIER);
                z = new FileLooker(TableUtil.tiPath).look(reader.getBuffer());
                if(z==0){
                    z = new FilePutter(TableUtil.tiPath).put(TableUtil.tiNumber, reader.getBuffer());
                }
                new FileOuter().out(TableUtil.tiNumber,z);
            }
        }
    }
}
