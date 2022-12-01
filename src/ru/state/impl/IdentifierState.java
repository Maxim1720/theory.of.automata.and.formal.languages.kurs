package ru.state.impl;

import ru.Reader;
import ru.file.FileLooker;
import ru.file.FileOuter;
import ru.file.FilePutter;
import ru.file.TableUtil;
import ru.state.State;
import ru.state.StateType;

public class IdentifierState extends State {

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
                    new FilePutter(TableUtil.tiPath).put(TableUtil.tiNumber, reader.getBuffer());
                }
                new FileOuter().out(TableUtil.tiNumber,z);
            }
        }
    }
}
