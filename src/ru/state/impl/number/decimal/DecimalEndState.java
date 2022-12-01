package ru.state.impl.number.decimal;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;
import ru.state.impl.number.hex.HexEndState;
import ru.state.impl.number.hex.HexState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class DecimalEndState extends State {

    private final HashMap<State, Function<Character, Boolean>> stateFunctionHashMap;

    public DecimalEndState(){
        stateFunctionHashMap = new HashMap<>();
        stateFunctionHashMap.put(new HexState(),
                character -> new HexState().is(character) || Character.isDigit(character));
        stateFunctionHashMap.put(new HexEndState(),new HexEndState()::is);

    }

    @Override
    public boolean is(char ch) {
        return Pattern.compile("[Dd]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.DEC_END);
        reader.add();
        reader.next();
        if(reader.charsExists()){
            for (Map.Entry<State, Function<Character, Boolean>> e : stateFunctionHashMap.entrySet()){
                if(e.getValue().apply(reader.getCurrent())){
                    return e.getKey().transit(reader);
                }
            }
            if(!Character.isWhitespace(reader.getCurrent()) && !reader.currentIsDelimiter()) {
                reader.setStateType(StateType.ERR);
            }
        }
        return reader;
    }
}
