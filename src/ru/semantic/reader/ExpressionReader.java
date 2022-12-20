package ru.semantic.reader;

import ru.util.TableUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ExpressionReader {
    public List<String> readAll(){
        String path = TableUtil.expressions;
        File file = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> expressions =  bufferedReader.lines().toList();
            bufferedReader.close();
            return expressions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
