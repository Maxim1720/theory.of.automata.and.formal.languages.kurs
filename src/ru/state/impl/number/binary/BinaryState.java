package ru.state.impl.number.binary;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;
import ru.state.impl.exp.ExpState;
import ru.state.impl.number.FloatState;
import ru.state.impl.number.decimal.DecimalEndState;
import ru.state.impl.number.decimal.DecimalState;
import ru.state.impl.number.hex.HexEndState;
import ru.state.impl.number.hex.HexState;
import ru.state.impl.number.oct.OctEndState;
import ru.state.impl.number.oct.OctState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class BinaryState extends State {
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
