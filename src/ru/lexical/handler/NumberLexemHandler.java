package ru.lexical.handler;

import ru.lexical.file.FileOuter;
import ru.lexical.file.FilePutter;
import ru.lexical.file.TableUtil;

public class NumberLexemHandler {

    public void handle(String lexem){
        new FileOuter().out(TableUtil.tnNumber,
                new FilePutter(TableUtil.tnPath)
                        .put(TableUtil.tnNumber,lexem));
    }

}
