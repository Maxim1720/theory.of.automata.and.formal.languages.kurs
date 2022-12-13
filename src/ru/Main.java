package ru;

import ru.lexical.Analyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        flushFiles();
        new Analyzer().analyze();
    }

    public static void flushFiles(){
        String[] names = new String[]{
                "/home/almat/IdeaProjects/LexAnalyzer/src/res/out_table/lex_table.txt",
                "/home/almat/IdeaProjects/LexAnalyzer/src/res/source/tn.txt",
                "/home/almat/IdeaProjects/LexAnalyzer/src/res/source/ti.txt"
        };

        for (String s: names){
            File file = new File(s);
            if(file.exists()){
                try(FileWriter fw = new FileWriter(file)) {
                    fw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}