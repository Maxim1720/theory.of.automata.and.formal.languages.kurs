package ru.state.impl.number.oct;

import ru.Reader;
import ru.state.State;
import ru.state.StateType;
import ru.state.impl.exp.ExpState;
import ru.state.impl.number.FloatState;
import ru.state.impl.number.decimal.DecimalEndState;
import ru.state.impl.number.decimal.DecimalState;
import ru.state.impl.number.hex.HexEndState;
import ru.state.impl.number.hex.HexState;
import ru.state.transitor.NumberTransitor;

import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

public class OctState extends State {

    private final HashMap<State, Function<Character, Boolean>> stateFunctionHashMap;

    public OctState(){

        stateFunctionHashMap = new HashMap<>();
        stateFunctionHashMap.put(new OctEndState(), new OctEndState()::is);
        stateFunctionHashMap.put(new DecimalState(), new DecimalState()::is);
        stateFunctionHashMap.put(new ExpState(), new ExpState()::is);
        stateFunctionHashMap.put(new DecimalEndState(), new DecimalEndState()::is);
        stateFunctionHashMap.put(new HexState(), character ->
                Pattern.compile("[ABCF]").matcher(String.valueOf(character)).matches());
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
        return new NumberTransitor(stateFunctionHashMap).transit(reader);
    }
}
