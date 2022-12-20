package ru.semantic.reader;

import ru.util.TableUtil;

public class ConditionalReader extends Reader{
    @Override
    protected String getFilePath() {
        return TableUtil.conditionals;
    }
}
