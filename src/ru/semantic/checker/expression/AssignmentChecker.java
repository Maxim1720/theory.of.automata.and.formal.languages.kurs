package ru.semantic.checker.expression;

import ru.exception.sem.SemanticException;
import ru.semantic.reader.DescriptionReader;
import ru.util.TableUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class AssignmentChecker {

    public void checkDescriptions(){

        LinkedList<String> onlyIdentifiers = onlyIdentifiers();
        while (onlyIdentifiers.size() > 0){
            String s = onlyIdentifiers.getFirst();
            onlyIdentifiers.remove(s);
            if(onlyIdentifiers.contains(s)){
                throw new SemanticException(" identifier '" + s + "' has already been defined.");
            }
        }

        List<String> identifiers = allIdentifiers();
        for (String s: identifiers){
            if(!onlyIdentifiers().contains(s)){
                throw new SemanticException("Undeclared identifier: '" + s + "'");
            }
        }
    }

    private LinkedList<String> onlyIdentifiers(){
        LinkedList<String> descriptions = new LinkedList<>(new DescriptionReader().readAll());
        LinkedList<String> onlyIdentifiers = new LinkedList<>();
        for (String s : descriptions){
            onlyIdentifiers.add(s.split(" ")[0]);
        }
        return onlyIdentifiers;
    }

    private List<String> allIdentifiers(){
        try {
            return new BufferedReader(new FileReader(TableUtil.tiPath)).lines().toList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
