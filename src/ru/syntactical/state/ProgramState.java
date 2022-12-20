package ru.syntactical.state;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.reader.LexemeReaderImpl;
import ru.syntactical.state.operator.Assignment;
import ru.syntactical.state.operator.Composite;
import ru.syntactical.state.operator.Operator;

import java.util.ArrayList;
import java.util.List;

public class ProgramState implements State {

    List<State> innerStates;

    public ProgramState(){
        innerStates = new ArrayList<>();
        //innerStates.add(new Description());
        innerStates.add(new Composite());
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.eq("{");
    }

    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        lexemeReader.next();
        boolean is = true;
        while (is) {
            if(new Composite().is(lexemeReader)){
                lexemeReader = new Composite().transit(lexemeReader);
            }
            expect(lexemeReader, ";");
            lexemeReader.next();
            is = hasNextProgramBody(lexemeReader);
        }
        expect(lexemeReader, "}");
        return lexemeReader;
    }

    private boolean hasNextProgramBody(LexemeReader lexemeReader){
        innerStates.add(new Description(lexemeReader.current()));
        for (State s : innerStates) {
            if (s.is(lexemeReader)) {
                return true;
            }
        }
        return false;
    }

    public void expect(LexemeReader lexemeReader, String expectedLexeme){
        if(!lexemeReader.eq(expectedLexeme)){
            throw new SyntacticException(lexemeReader.current(), expectedLexeme);
        }
    }

}
