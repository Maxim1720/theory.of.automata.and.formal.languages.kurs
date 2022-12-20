package ru.semantic.checker.operation;

import ru.semantic.checker.operation.AssignmentChecker;

import java.util.List;

public class OperationChecker {

    List<String> operations;

    public OperationChecker(){

    }

    public void check(){
        new AssignmentChecker().check();

    }

}
