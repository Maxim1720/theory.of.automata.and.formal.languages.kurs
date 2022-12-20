package ru.syntactical.state.operator;

import ru.exception.syn.SyntacticException;
import ru.semantic.reader.ExpressionReader;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.state.Description;
import ru.syntactical.state.expression.Expression;
import ru.syntactical.writer.AssignmentWriter;

public class Assignment extends AbsOperator {
    @Override
    public LexemeReader tryTransit(LexemeReader lexemeReader) {
        String identifier = lexemeReader.current();

        lexemeReader.next();
        if(lexemeReader.eq("as")){
            lexemeReader.next();

            if(new Expression().is(lexemeReader)){
                lexemeReader = new Expression().transit(lexemeReader);
                new AssignmentWriter().out(
                        identifier
                                + " as "
                                + new ExpressionReader().readAll().get(
                                new ExpressionReader().readAll().size()-1
                        )
                                +"\n");
            }
            else{
                throw new SyntacticException(lexemeReader.current(), "[EXPRESSION]");
            }
            //new AssignmentWriter().out("\n");
        } else if (lexemeReader.eq(",") || lexemeReader.eq(":")) {
//            new DescriptionOuter().out(identifier);
            lexemeReader = new Description(identifier).transit(lexemeReader);
        } else {
            throw new SyntacticException(lexemeReader.current(), "[as|,|:]");
        }

        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        //return /*lexemeReader.identifier();*/lexemeReader.eq("as");
        return lexemeReader.identifier();
    }
}
