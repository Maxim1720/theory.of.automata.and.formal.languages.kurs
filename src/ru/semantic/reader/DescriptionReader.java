package ru.semantic.reader;

import ru.util.TableUtil;

import java.io.*;
import java.util.List;

public class DescriptionReader {
    public List<String> readAll(){
        String path = TableUtil.descriptions;
        File file = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> descriptions =  bufferedReader.lines().toList();
            bufferedReader.close();
            return descriptions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
