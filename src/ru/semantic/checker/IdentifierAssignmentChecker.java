package ru.semantic.checker;

import ru.lexical.file.FileLooker;
import ru.semantic.reader.DescriptionReader;
import ru.util.TableUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class IdentifierAssignmentChecker {

    public boolean previouslyIdentifier(String expression, String id){

        List<String> expressionLexemeTable = expressionAsLexemeTable(expression);
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(TableUtil.lexTable));
            StringBuilder lexTableStr = new StringBuilder();
            for (String s: bufferedReader.lines().toList()){
                lexTableStr.append(s).append(" ");
            }
            lexTableStr = new StringBuilder(lexTableStr.toString().trim());

            StringBuilder expressionLexTableSubstring = new StringBuilder();
            for (String s: expressionLexemeTable){
                expressionLexTableSubstring.append(s).append(" ");
            }
            expressionLexTableSubstring = new StringBuilder(expressionLexTableSubstring.toString().trim());

            int index = lexTableStr.indexOf(expressionLexTableSubstring.toString());
            int z = new FileLooker(TableUtil.tiPath).look(id);


            String idSubstring = TableUtil.tiNumber + " " + z;
            if(expressionLexTableSubstring.toString().equals(idSubstring)){
                idSubstring += " "
                        + TableUtil.tlNumber+ " "
                        + new FileLooker(TableUtil.tlPath).look(":");
            }
            int idIndex = lexTableStr.indexOf(idSubstring);
            return index >= idIndex && idIndex != -1 && index != -1;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> describedWithoutType(){
        List<String> list = new DescriptionReader().readAll();
        List<String> onlyIdentifiers = new ArrayList<>();
        for (String s: list){
            onlyIdentifiers.add(s.split(" ")[0]);
        }
        return onlyIdentifiers;
    }

    private List<String> expressionAsLexemeTable(String expression){

        List<String> expressionLexemeTable = new ArrayList<>();

        String[] expressionStrs = expression.split(" ");

        for (String s: expressionStrs) {
            int t = TableUtil.twNumber;
            int z = new FileLooker(TableUtil.twPath).look(s);
            if(z==0){
                t = TableUtil.tlNumber;
                z = new FileLooker(TableUtil.tlPath).look(s);
                if(z==0){
                    t = TableUtil.tiNumber;
                    z = new FileLooker(TableUtil.tiPath).look(s);
                    if(z==0){
                        t = TableUtil.tnNumber;
                        z = new FileLooker(TableUtil.tnPath).look(s);
                    }
                }
            }
            expressionLexemeTable.add(t + " " + z);
        }

        return expressionLexemeTable;

    }

}
