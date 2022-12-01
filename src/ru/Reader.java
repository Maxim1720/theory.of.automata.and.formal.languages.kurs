package ru;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Reader {

    private final BufferedReader bufferedReader;
    private char ch;

    public Reader() {
        try {
            String codePath = "/home/almat/IdeaProjects/LexAnalyzer/src/res/code.txt";
            bufferedReader = new BufferedReader(new FileReader(Paths.get(
                    codePath).toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean charsExists()  {
        try {
            return bufferedReader.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public char getCurrent(){
        return ch;
    }

    public void next(){
        ch = gc();
    }
    private char gc(){
        try {
            int c = bufferedReader.read();
            if(c == -1){
                c = Character.MAX_VALUE;
            }
            return (char) c;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
