package ru.lexical.state.impl.exp;

import ru.lexical.Reader;
import ru.lexical.file.FileOuter;
import ru.lexical.file.FilePutter;
import ru.lexical.file.TableUtil;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.number.hex.HexEndState;
import ru.lexical.state.impl.number.hex.HexState;

import java.util.regex.Pattern;

public class ExpState implements State {
    @Override
    public boolean is(char ch) {
        return Pattern.compile("[Ee]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.EXP);
        reader.add();
        reader.next();

        HexState hexState = new HexState();
        HexEndState hexEndState = new HexEndState();

        if (reader.getCurrent() == '+'
                || reader.getCurrent() == '-'){
            reader.add();
            reader.next();
            while (reader.charsExists()
                    && Character.isDigit(reader.getCurrent())){
                reader.add();
                reader.next();
            }

            if (!Character.isWhitespace(reader.getCurrent())
                    && !reader.currentIsDelimiter()){
                reader.setStateType(StateType.ERR);
            }
            new FileOuter().out(TableUtil.tnNumber,
                    new FilePutter(TableUtil.tnPath)
                            .put(TableUtil.tnNumber, reader.getBuffer()));
        }
        else if (Character.isDigit(reader.getCurrent())) {
            while (reader.charsExists() && Character.isDigit(reader.getCurrent())){
                reader.add();
                reader.next();
            }

            if(/*isHex(reader.getCurrent())*/hexState.is(reader.getCurrent())){
                //hexState();
                reader = hexState.transit(reader);
            }
            else if (/*isHexEnd(reader.getCurrent())*/hexEndState.is(reader.getCurrent())){
//                hexEndState();
                reader = hexEndState.transit(reader);
            }
            else if(!Character.isWhitespace(reader.getCurrent()) && !reader.currentIsDelimiter()) {
                reader.add();
                reader.setStateType(StateType.ERR);
            }
            else{
                new FileOuter().out(TableUtil.tnNumber,
                        new FilePutter(TableUtil.tnPath)
                                .put(TableUtil.tnNumber, reader.getBuffer()));
            }
        }
        else if (hexState.is(reader.getCurrent())){
//            hexState();
            reader = hexState.transit(reader);
        } else if (hexEndState.is(reader.getCurrent())) {
//            hexEndState();
            reader = hexEndState.transit(reader);
        }
        return reader;
    }
}
