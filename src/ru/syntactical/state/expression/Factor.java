package ru.syntactical.state.expression;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.writer.ExpressionOuter;

import java.util.ArrayList;
import java.util.List;

public class Factor implements State {

    private final String unary = "not";

    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        ExpressionOuter eo = new ExpressionOuter();
        eo.out(lexemeReader.current());
        if (lexemeReader.eq(unary)){
            lexemeReader.next();
            if(new Factor().is(lexemeReader)){
                eo.out(" ");
                //eo.out(lexemeReader.current());
                lexemeReader = new Factor().transit(lexemeReader);

            }else {
                throw new SyntacticException(lexemeReader.current(), "[FACTOR]");
            }
        }
        else {
            //eo.out(" ");
            lexemeReader.next();
        }
        //eo.out(" ");
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.identifier()
                || lexemeReader.number()
                || new LogicConst().is(lexemeReader)
                || lexemeReader.eq(unary)
                || new Expression().is(lexemeReader);
    }
}
