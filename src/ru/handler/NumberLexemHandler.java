package ru.handler;

import ru.file.FileOuter;
import ru.file.FilePutter;
import ru.file.TableUtil;

public class NumberLexemHandler {

    public void handle(String lexem){
        new FileOuter().out(TableUtil.tnNumber,
                new FilePutter(TableUtil.tnPath)
                        .put(TableUtil.tnNumber,lexem));
    }

}
