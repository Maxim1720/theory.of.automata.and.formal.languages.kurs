package ru.syntactical.state.expression;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.SyntacticalAnalyzer;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.state.expression.operand.Addition;
import ru.syntactical.state.expression.operand.Operand;
import ru.syntactical.writer.ExpressionOuter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression implements State {

    private String[] relationGroupOperators;

    public Expression(){
        relationGroupOperators = new String[]{
                "<>",
                "=",
                "<",
                ">",
                ">=",
                "<="
        };
    }

    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        ExpressionOuter eo = new ExpressionOuter();
        //eo.out(lexemeReader.current());
        lexemeReader = new Operand().transit(lexemeReader);
        //eo.out(lexemeReader.current());
        while (Arrays.stream(relationGroupOperators).toList().contains(lexemeReader.current())){
            eo.out(" ");
            eo.out(lexemeReader.current());

            lexemeReader.next();
            if(new Operand().is(lexemeReader)){
                eo.out(" ");
                lexemeReader = new Operand().transit(lexemeReader);
            }
            else{
                throw new SyntacticException(lexemeReader.current(), "[OPERAND]");
            }
        }
        eo.out("\n");
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return new Operand().is(lexemeReader);
    }
}
