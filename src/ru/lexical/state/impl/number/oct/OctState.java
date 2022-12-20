package ru.lexical.state.impl.number.oct;

import ru.lexical.Reader;
import ru.lexical.state.State;
import ru.lexical.state.StateType;
import ru.lexical.state.impl.exp.ExpState;
import ru.lexical.state.impl.number.FloatState;
import ru.lexical.state.impl.number.decimal.DecimalEndState;
import ru.lexical.state.impl.number.decimal.DecimalState;
import ru.lexical.state.impl.number.hex.HexEndState;
import ru.lexical.state.impl.number.hex.HexState;
import ru.lexical.state.transitor.NumberTransit;

import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

public class OctState implements State {

    private final HashMap<State, Function<Character, Boolean>> stateFunctionHashMap;

    public OctState(){
        stateFunctionHashMap = new HashMap<>();
        stateFunctionHashMap.put(new OctEndState(), new OctEndState()::is);
        stateFunctionHashMap.put(new DecimalState(), new DecimalState()::is);
        stateFunctionHashMap.put(new ExpState(), new ExpState()::is);
        stateFunctionHashMap.put(new DecimalEndState(), new DecimalEndState()::is);
        stateFunctionHashMap.put(new HexState(), character ->
                Pattern.compile("[ABCFabcf]").matcher(String.valueOf(character)).matches());
        stateFunctionHashMap.put(new HexEndState(), new HexEndState()::is);
        stateFunctionHashMap.put(new FloatState(), new FloatState()::is);

    }

    @Override
    public boolean is(char ch) {
        return Pattern.compile("[2-7]").matcher(String.valueOf(ch)).matches();
    }

    @Override
    public Reader transit(Reader reader) {
        reader.setStateType(StateType.OCT);
        Pattern pattern = Pattern.compile("[0-7]");
        while (reader.charsExists()
                && pattern.matcher(String.valueOf(reader.getCurrent())).matches()){
            reader.add();
            reader.next();
        }
        return new NumberTransit(stateFunctionHashMap).transit(reader);
    }
}