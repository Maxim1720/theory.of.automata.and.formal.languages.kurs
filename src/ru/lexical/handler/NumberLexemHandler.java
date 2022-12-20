package ru.lexical.handler;

import ru.lexical.file.FileLooker;
import ru.lexical.file.FileOuter;
import ru.lexical.file.FilePutter;
import ru.util.TableUtil;

public class NumberLexemHandler {

    public void handle(String lexem){

        int z = new FileLooker(TableUtil.tnPath).look(lexem);
        if(z == 0) {
            z = new FilePutter(TableUtil.tnPath)
                    .put(TableUtil.tnNumber, lexem);
        }
        new FileOuter().out(TableUtil.tnNumber, z);
    }

}
