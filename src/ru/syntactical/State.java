package ru.syntactical;

import ru.syntactical.reader.LexemeReader;
import ru.syntactical.reader.LexemeReaderImpl;

public interface State {
    LexemeReader transit(LexemeReader lexemeReader);
    boolean is(LexemeReader lexemeReader);
}
