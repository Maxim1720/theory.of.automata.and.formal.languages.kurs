package ru.lexical.file;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public class FileLooker {

    private final String path;

    public FileLooker(String path){
        this.path = path;
    }

    public int look(String lexem){
        int z;
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            z = searchIndex(lines(), lexem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return z;
    }

    private int searchIndex(List<String> lines, String lex){
        for (String s: lines){
            if(s.equals(lex)){
                return lines.indexOf(s)+1;
            }
        }
        return 0;
    }

    private List<String> lines() throws FileNotFoundException {
        BufferedReader bufferedReader
                = new BufferedReader(
                new FileReader(
                        Paths.get(path)
                                .toFile()));
        return bufferedReader
                .lines().toList();
    }

}
