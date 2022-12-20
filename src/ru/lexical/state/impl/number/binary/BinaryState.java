package ru.lexical.state.impl.number.binary;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.exp.ExpState;
import ru.lexical.state.impl.number.FloatState;
import ru.lexical.state.impl.number.decimal.DecimalEndState;
import ru.lexical.state.impl.number.decimal.DecimalState;
import ru.lexical.state.impl.number.hex.HexEndState;
import ru.lexical.state.impl.number.hex.HexState;
import ru.lexical.state.impl.number.oct.OctEndState;
import ru.lexical.state.impl.number.oct.OctState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class BinaryState implements State {
    HashMap<State, Function<Character,Boolean>> states;
    public BinaryState() {

        states = new HashMap<>();

        states.put(new OctState(), new OctState()::is);
        states.put(new DecimalState(), new DecimalState()::is);
        states.put(new FloatState(),new FloatState()::is);
        states.put(new ExpState(), new ExpState()::is);
        states.put(new DecimalEndState(),new DecimalEndState()::is);
        states.put(new OctEndState(), new OctEndState()::is);
        states.put(new HexState(),
                character -> Pattern.compile("[ACFacf]").matcher(String.valueOf(character)).matches());
        states.put(new HexEndState(), new HexEndState()::is);
        states.put(new BinaryEndState(), new BinaryEndState()::is);
    }

    @Override
    public boolean is(char ch) {
        return Pattern.compile("[01]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.BINARY);
        while (reader.charsExists() && is(reader.getCurrent())) {
            reader.add();
            reader.next();
        }
        for (Map.Entry<State, Function<Character, Boolean>> e : states.entrySet()) {
            if (e.getValue().apply(reader.getCurrent())) {
                return e.getKey().transit(reader);
            }
        }
        if(Pattern.matches("\\d+",reader.getBuffer())) {
            reader = new DecimalState().transit(reader);
        }
        if(!Character.isDigit(reader.getCurrent())
                && !reader.currentIsDelimiter()
                && !Character.isWhitespace(reader.getCurrent())) {
            return error(reader);
        }
        return reader;
    }

    private Reader error(Reader reader){
        reader.add();
        reader.setStateType(StateType.ERR);
        return reader;
    }
}
