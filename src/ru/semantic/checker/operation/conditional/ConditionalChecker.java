package ru.semantic.checker.operation.conditional;

import ru.exception.sem.SemanticException;
import ru.semantic.checker.expression.ExpressionSignTypeChecker;
import ru.semantic.reader.ConditionalReader;

import java.util.List;

public class ConditionalChecker {

    private final ConditionalReader conditionalReader;

    public ConditionalChecker(){
        conditionalReader = new ConditionalReader();
    }

    public void check(){
        checkBooleans();
    }

    private void checkBooleans(){
        List<String> lines = conditionalReader.readLines();
        for (String s: lines){
            if(!new ExpressionSignTypeChecker().getTypeFromExpression(s).equals("boolean")){
                throw new SemanticException("Incorrect condition: '" + s + "' isn't boolean");
            }
        }
    }

}
