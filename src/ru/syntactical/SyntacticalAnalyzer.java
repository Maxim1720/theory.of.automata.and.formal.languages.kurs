package ru.syntactical;

import ru.Analyzer;
import ru.exception.syn.SyntacticException;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.reader.LexemeReaderImpl;
import ru.syntactical.state.ProgramState;

public class SyntacticalAnalyzer implements Analyzer {
    @Override
    public void analyze() {
        ProgramState programState = new ProgramState();
        LexemeReader lexemeReader = new LexemeReaderImpl();
        if(programState.is(lexemeReader)){
            programState.transit(lexemeReader);
        }
        else{
            throw new SyntacticException(lexemeReader.current(), "{");
        }
    }
}
