package ru.syntactical.state.expression;

import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;

public class LogicConst implements State {
    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        lexemeReader.next();
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.eq("true") || lexemeReader.eq("false");
    }
}
