package ru.lexical.state.transitor;

import ru.lexical.Reader;
import ru.lexical.handler.NumberLexemHandler;
import ru.lexical.state.State;
import ru.lexical.state.StateType;

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
                //return e.getKey().transit(reader);
                reader = e.getKey().transit(reader);
                break;
            }
        }

        if (!Character.isWhitespace(reader.getCurrent())
                && !reader.currentIsDelimiter()){
            reader.add();
            reader.setStateType(StateType.ERR);
        }
        else {
        //    new NumberLexemHandler().handle(reader.getBuffer());
        }

        return reader;
    }

}
