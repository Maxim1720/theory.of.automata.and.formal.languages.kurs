package ru.lexical.state.impl.number.decimal;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.exp.ExpState;
import ru.lexical.state.impl.number.FloatState;
import ru.lexical.state.impl.number.hex.HexEndState;
import ru.lexical.state.impl.number.hex.HexState;
import ru.lexical.state.transitor.NumberTransit;

import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

public class DecimalState implements State {

    private final HashMap<State, Function<Character, Boolean>> stateFunctionHashMap;
    public DecimalState(){

        stateFunctionHashMap = new HashMap<>();
        stateFunctionHashMap.put(new HexState(), character ->
                Pattern.compile("[ABCFabcf]")
                        .matcher(String.valueOf(character)).matches());
        stateFunctionHashMap.put(new FloatState(), new FloatState()::is);
        stateFunctionHashMap.put(new ExpState(), new ExpState()::is);
        stateFunctionHashMap.put(new HexEndState(), new HexEndState()::is);
        stateFunctionHashMap.put(new DecimalEndState(), new DecimalEndState()::is);
    }

    @Override
    public boolean is(char ch) {
        return Pattern.compile("[89]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.DEC);
        while (reader.charsExists() && Character.isDigit(reader.getCurrent())){
            reader.add();
            reader.next();
        }

        return new NumberTransit(stateFunctionHashMap).transit(reader);
    }
}
