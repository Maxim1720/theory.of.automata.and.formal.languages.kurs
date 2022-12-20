package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.lexical.Reader;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;

public class Input extends AbsOperator {

    @Override
    protected LexemeReader tryTransit(LexemeReader lexemeReader) {
        lexemeReader.next();
        if(lexemeReader.eq("(")) {
            lexemeReader.next();
            if(lexemeReader.identifier()){
                lexemeReader.next();
                while (lexemeReader.eq(",")){
                    lexemeReader.next();
                    if(!lexemeReader.identifier()){
                        throw new SyntacticException(lexemeReader.current(),
                                "[IDENTIFIER]");
                    }
                    else {
                        lexemeReader.next();
                    }
                }
                if(lexemeReader.eq(")")){
                    lexemeReader.next();
                }
            }
        }
        else {
            throw new SyntacticException(lexemeReader.current(), "(");
        }

        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.eq("read");
    }
}
