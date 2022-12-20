package ru.syntactical.state.expression.operand;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.writer.ExpressionOuter;

import java.util.Arrays;

public class Operand implements State {
    private String[] additionOperatorGroup;

    public Operand(){
        additionOperatorGroup = new String[]{
                "+","-","or"
        };
    }

    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        ExpressionOuter eo = new ExpressionOuter();
        //eo.out(lexemeReader.current());
        lexemeReader = new Addition().transit(lexemeReader);
        //eo.out(lexemeReader.current());
        while (Arrays.stream(additionOperatorGroup).toList().contains(lexemeReader.current())) {
            eo.out(" ");
            eo.out(lexemeReader.current());
            lexemeReader.next();
            if (new Addition().is(lexemeReader)) {
                eo.out(" ");
                lexemeReader = new Addition().transit(lexemeReader);
                //eo.out(" ");
            }
            else{
                throw new SyntacticException(lexemeReader.current(), "[ADDITION]");
            }
        }
        //eo.out(" ");
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return new Addition().is(lexemeReader);
    }
}
