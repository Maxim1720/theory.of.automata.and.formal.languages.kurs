package ru.syntactical.writer;

import ru.util.TableUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AssignmentWriter {

    public void out(String lexeme) {
        String path = TableUtil.assignments;
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
