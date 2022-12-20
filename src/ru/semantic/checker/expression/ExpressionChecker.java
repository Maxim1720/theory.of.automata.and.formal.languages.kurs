package ru.semantic.checker.expression;

import ru.exception.sem.SemanticException;
import ru.lexical.file.FileLooker;
import ru.semantic.checker.IdentifierAssignmentChecker;
import ru.semantic.reader.DescriptionReader;
import ru.semantic.reader.ExpressionReader;
import ru.util.TableUtil;

import java.util.List;

public class ExpressionChecker {


    public void checkExpressions(){
        List<String> expressions = new ExpressionReader().readAll();
        checkDeclarations(expressions);
        new ExpressionSignTypeChecker().checkSignAndTypes(expressions);
    }



    private void checkDeclarations(List<String> expressions) {
        for (String expression : expressions) {
            String[] strings = expression.split(" ");
            for (String str : strings) {
                if (str.matches("[a-zA-Z][a-zA-Z\\d]*")) {
                    if(isIdentifier(str)){
                        if (!new IdentifierAssignmentChecker()
                                .previouslyIdentifier(expression, str)) {
                            throw new SemanticException("Undeclared identifier: '" + str + "'");
                        }
                    }
                }
            }
        }
    }

    private boolean isIdentifier(String lexeme){
        return new FileLooker(TableUtil.tiPath).look(lexeme) != 0;
    }
}
