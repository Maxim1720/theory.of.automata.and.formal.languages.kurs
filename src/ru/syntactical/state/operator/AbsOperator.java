package ru.syntactical.state.operator;

import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.writer.AssignmentWriter;

public abstract class AbsOperator implements State {

    private final AssignmentWriter assignmentWriter;

    public AbsOperator(){
        assignmentWriter = new AssignmentWriter();
    }

    public LexemeReader transit(LexemeReader lexemeReader){
        //operationWriter.out(lexemeReader.current());
        return tryTransit(lexemeReader);
    }

    protected abstract LexemeReader tryTransit(LexemeReader lexemeReader);

}
