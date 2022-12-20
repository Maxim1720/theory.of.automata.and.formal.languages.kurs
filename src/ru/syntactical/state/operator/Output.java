package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.lexical.Reader;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.state.expression.Expression;

public class Output extends AbsOperator {

    @Override
    protected LexemeReader tryTransit(LexemeReader lexemeReader) {
        lexemeReader.next();

        if(lexemeReader.eq("(")){
            lexemeReader.next();
            if(new Expression().is(lexemeReader)){
                //lexemeReader.next();
                lexemeReader = new Expression().transit(lexemeReader);

                while (lexemeReader.eq(",")){
                    lexemeReader.next();
                    if(!new Expression().is(lexemeReader)){
                        throw new SyntacticException(lexemeReader.current(), "[EXPRESSION]");
                    }
                    lexemeReader.next();
                }
                if(lexemeReader.eq(")")){
                    lexemeReader.next();
                }
                else {
                    throw new SyntacticException(lexemeReader.current(), ")");
                }
            }
        }

        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.eq("write");
    }
}
