package ru.semantic;

import ru.Analyzer;
import ru.semantic.checker.expression.AssignmentChecker;
import ru.semantic.checker.operation.OperationChecker;
import ru.semantic.checker.expression.ExpressionChecker;
import ru.semantic.checker.operation.conditional.ConditionalChecker;

public class SemanticAnalyzer implements Analyzer {
    @Override
    public void analyze() {
        new AssignmentChecker().checkDescriptions();
        new ExpressionChecker().checkExpressions();
        new OperationChecker().check();
        new ConditionalChecker().check();
    }

}
