package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.state.expression.Expression;

public class FixCycle extends AbsOperator {
    @Override
    protected LexemeReader tryTransit(LexemeReader lexemeReader) {
        lexemeReader.next();
        if(new Assignment().is(lexemeReader)){
            lexemeReader = new Assignment().transit(lexemeReader);
            if(lexemeReader.eq("to")){
                lexemeReader.next();
                if(new Expression().is(lexemeReader)){
                    lexemeReader = new Expression().transit(lexemeReader);
                    if(lexemeReader.eq("do")){
                        lexemeReader.next();
                        if(new Operator().is(lexemeReader)) {
                            lexemeReader = new Operator().transit(lexemeReader);
                        }
                        else{
                            throw new SyntacticException(lexemeReader.current(), "[OPERATOR]");
                        }
                    }
                    else{
                        throw new SyntacticException(lexemeReader.current(), "do");
                    }
                }
                else {
                    throw new SyntacticException(lexemeReader.current(), "[EXPRESSION]");
                }
            }
            else{
                throw new SyntacticException(lexemeReader.current(), "to");
            }
        }
        else{
            throw new SyntacticException(lexemeReader.current(), "[IDENTIFIER]");
        }

        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.eq("for");
    }
}
