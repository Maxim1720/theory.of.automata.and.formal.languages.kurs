package ru.syntactical.writer;

import ru.util.TableUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConditionalWriter {
    public void out(String lexeme) {
        String path = TableUtil.conditionals;
        File file = new File(path);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            /*if(file.length() > 0){
                bufferedWriter.newLine();
            }*/
            bufferedWriter.append(lexeme);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
