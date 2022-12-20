package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;

public class Composite extends AbsOperator {
    @Override
    protected LexemeReader tryTransit(LexemeReader lexemeReader) {
            lexemeReader = new Operator().transit(lexemeReader);
            while (lexemeReader.eq(":") || lexemeReader.eq("\n")){
                lexemeReader.next();
                if(new Operator().is(lexemeReader)){
                    lexemeReader = new Operator().transit(lexemeReader);
                }
                else {
                    throw new SyntacticException(lexemeReader.current(), "[OPERATOR]");
                }
            }
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        //return lexemeReader.eq(":") || lexemeReader.eq("\n");
        return new Operator().is(lexemeReader);
    }
}
