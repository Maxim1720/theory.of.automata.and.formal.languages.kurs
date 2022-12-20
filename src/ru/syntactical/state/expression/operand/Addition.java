package ru.syntactical.state.expression.operand;

import ru.exception.syn.SyntacticException;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.state.expression.Factor;
import ru.syntactical.writer.ExpressionOuter;

import java.util.List;

public class Addition implements State {

    private String[] multipleOperatorGroup;


    public Addition(){
        multipleOperatorGroup = new String[]{
          "*","/","and"
        };

    }

    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        //lexemeReader.next();
        ExpressionOuter eo = new ExpressionOuter();
        //eo.out(lexemeReader.current());

        lexemeReader = new Factor().transit(lexemeReader);
        while (List.of(multipleOperatorGroup).contains(lexemeReader.current())){
            eo.out(" ");
            eo.out(lexemeReader.current());
            lexemeReader.next();
            if(new Factor().is(lexemeReader)){
                eo.out(" ");
                lexemeReader = new Factor().transit(lexemeReader);
                //eo.out(" ");
            }
            else {
                throw new SyntacticException(lexemeReader.current(), "[IDENTIFIER|NUMBER|not|EXPRESSION]");
            }
        }
        //eo.out(" ");
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return new Factor().is(lexemeReader);
    }
}
