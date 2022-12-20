package ru.semantic.reader;

import ru.util.TableUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class Reader {

    public List<String> readLines(){
        String path = getFilePath();
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

    protected abstract String getFilePath();

}
