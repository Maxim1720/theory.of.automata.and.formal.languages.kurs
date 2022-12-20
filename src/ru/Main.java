package ru;

import ru.exception.ParseException;
import ru.lexical.LexicalAnalyzer;
import ru.util.TableUtil;
import ru.semantic.SemanticAnalyzer;
import ru.syntactical.SyntacticalAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        flushFiles();
        try {
            new LexicalAnalyzer().analyze();
            System.out.println("Лексический разбор прошел успешно");
            new SyntacticalAnalyzer().analyze();
            System.out.println("Синтаксический разбор прошел успешно");
            new SemanticAnalyzer().analyze();
            System.out.println("Семантический разбор прошел успешно");
        }
        catch (ParseException e){
            System.out.println("Ошибка разбора: " + e.getMessage());
        }

    }

    public static void flushFiles(){
        String[] names = new String[]{
                TableUtil.lexTable,
                TableUtil.tnPath,
                TableUtil.tiPath,
                TableUtil.lexemes,
                TableUtil.descriptions,
                TableUtil.expressions,
                TableUtil.assignments,
                TableUtil.conditionals
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