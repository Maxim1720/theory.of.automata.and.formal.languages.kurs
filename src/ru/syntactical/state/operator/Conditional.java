package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.semantic.reader.ExpressionReader;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.state.expression.Expression;
import ru.syntactical.writer.ConditionalWriter;

public class Conditional extends AbsOperator {
    @Override
    public LexemeReader tryTransit(LexemeReader lexemeReader) {
        lexemeReader.next();
        if (new Expression().is(lexemeReader)) {
            lexemeReader = new Expression().transit(lexemeReader);
            new ConditionalWriter().out(new ExpressionReader().readAll().get(
                    new ExpressionReader().readAll().size()-1
            ) + "\n");
            if (lexemeReader.eq("then")) {
                lexemeReader.next();
                lexemeReader = new Operator().transit(lexemeReader);
                if (lexemeReader.eq("else")) {
                    lexemeReader.next();
                    if (new Operator().is(lexemeReader)) {
                        lexemeReader = new Operator().transit(lexemeReader);
                    } else {
                        throw new SyntacticException(lexemeReader.current(), "[OPERATOR]");
                    }
                }
            } else {
                throw new SyntacticException(lexemeReader.current(), "then");
            }
        } else {
            throw new SyntacticException(lexemeReader.current(), "[EXPRESSION]");
        }

        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.eq("if");
    }
}
