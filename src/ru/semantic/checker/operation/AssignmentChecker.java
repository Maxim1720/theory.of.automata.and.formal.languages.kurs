package ru.semantic.checker.operation;

import ru.exception.sem.SemanticException;
import ru.semantic.checker.IdentifierAssignmentChecker;
import ru.semantic.checker.expression.ExpressionSignTypeChecker;
import ru.semantic.reader.AssignmentOperationReader;
import ru.semantic.reader.DescriptionReader;
import ru.syntactical.writer.AssignmentWriter;
import ru.util.TypeConverter;

import java.util.List;
import java.util.Stack;

public class AssignmentChecker {

    AssignmentOperationReader assignmentOperationReader;

    public AssignmentChecker(){
        assignmentOperationReader = new AssignmentOperationReader();
    }

    public void check(){
        checkIdentifiersDefined();
        checkAssignmentTypes();
    }


    private void checkIdentifiersDefined(){
        List<String> lines = assignmentOperationReader.readLines();
        for (String s: lines){
            IdentifierAssignmentChecker checker = new IdentifierAssignmentChecker();
            if(!checker.previouslyIdentifier(s,s.split(" ")[0])
                    || !checker.describedWithoutType().contains(s.split(" ")[0])){
                throw new SemanticException("Undefined identifier: " + s);
            }
        }
    }



    private void checkAssignmentTypes(){
        List<String> lines = assignmentOperationReader.readLines();
        for(String s: lines){
            String[] strs = s.split(" as ");
            String s1 = strs[0];
            String s2 = strs[1];

            TypeConverter typeConverter = new TypeConverter();

            s1 = new ExpressionSignTypeChecker().getTypeFromExpression(s1);//typeConverter.replaceToType(s1);
            s2 = new ExpressionSignTypeChecker().getTypeFromExpression(s2);//typeConverter.replaceToType(s2);

            if(!s1.equals(s2)) {
                if (!(s1.equals("real")
                        && s2.equals("integer"))) {
                    throw new SemanticException("Cast error: '" + s1 + "' with '" + s2 + "' on line: " + s);
                }
            }
        }
    }
}
