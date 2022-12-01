package ru.state.impl.exp;

import ru.Reader;
import ru.file.FileOuter;
import ru.file.FilePutter;
import ru.file.TableUtil;
import ru.state.State;
import ru.state.StateType;
import ru.state.impl.number.hex.HexEndState;
import ru.state.impl.number.hex.HexState;

import java.util.regex.Pattern;

public class ExpState extends State {
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
