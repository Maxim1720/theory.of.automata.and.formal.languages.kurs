package ru.semantic.reader;

import ru.util.TableUtil;

public class AssignmentOperationReader extends Reader{
    @Override
    protected String getFilePath() {
        return TableUtil.assignments;
    }
}
