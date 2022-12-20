package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;

import java.util.ArrayList;
import java.util.List;

public class Operator implements State {

    private final List<AbsOperator> operators;
    public Operator(){
        operators = new ArrayList<>();
        operators.add(new Conditional());
        operators.add(new Assignment());
        operators.add(new FixCycle());
        operators.add(new ConditionalCycle());
        operators.add(new Input());
        operators.add(new Output());
    }


    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        for (State s: operators){
            if(s.is(lexemeReader)){
                //new OperationWriter().out(lexemeReader.current());
                return s.transit(lexemeReader);
            }
        }
        throw new SyntacticException(lexemeReader.current(), "[OPERATOR]");
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return  new Conditional().is(lexemeReader)
                || new Assignment().is(lexemeReader)
                || new ConditionalCycle().is(lexemeReader)
                || new FixCycle().is(lexemeReader)
                || new Input().is(lexemeReader)
                || new Output().is(lexemeReader);
    }
}
