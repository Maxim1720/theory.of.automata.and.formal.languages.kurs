package ru.state.transitor;

import ru.Reader;
import ru.handler.NumberLexemHandler;
import ru.state.State;
import ru.state.StateType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class NumberTransit {

    private final HashMap<State, Function<Character, Boolean>> stateFunctionHashMap;

    public NumberTransit(HashMap<State, Function<Character, Boolean>> stateFunctionHashMap){
        this.stateFunctionHashMap = stateFunctionHashMap;
    }

    public Reader transit(Reader reader){
        for(Map.Entry<State, Function<Character, Boolean>> e: stateFunctionHashMap.entrySet()){
            if(e.getValue().apply(reader.getCurrent())){
                return e.getKey().transit(reader);
            }
        }

        if (!Character.isWhitespace(reader.getCurrent())
                && !reader.currentIsDelimiter()){
            reader.add();
            reader.setStateType(StateType.ERR);
        }
        else {
            new NumberLexemHandler().handle(reader.getBuffer());
        }

        return reader;
    }

}
