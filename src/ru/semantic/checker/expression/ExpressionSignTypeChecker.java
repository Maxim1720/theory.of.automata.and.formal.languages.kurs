package ru.semantic.checker.expression;

import ru.exception.sem.SemanticException;
import ru.semantic.checker.IdentifierAssignmentChecker;
import ru.semantic.reader.DescriptionReader;
import ru.util.TypeConverter;

import java.util.*;
import java.util.regex.Pattern;

public class ExpressionSignTypeChecker {

    private List<DoubleOperation> doubleOperations;
    private Stack<String> stack;
    //private Stack<String> expression;
    public ExpressionSignTypeChecker(){
        doubleOperations = new ArrayList<>();
        doubleOperations.add(new DoubleOperation("+","integer","integer","integer"));
        doubleOperations.add(new DoubleOperation("+","real","real","real"));
        doubleOperations.add(new DoubleOperation("+", "integer", "real", "real"));
        doubleOperations.add(new DoubleOperation("+", "real", "integer", "real"));
        doubleOperations.add(new DoubleOperation("-","integer","integer","integer"));
        doubleOperations.add(new DoubleOperation("-","real","real","real"));
        doubleOperations.add(new DoubleOperation("-", "integer", "real", "real"));
        doubleOperations.add(new DoubleOperation("-", "real", "integer", "real"));
        doubleOperations.add(new DoubleOperation("*","integer","integer","integer"));
        doubleOperations.add(new DoubleOperation("*","real","real","real"));
        doubleOperations.add(new DoubleOperation("*", "integer", "real", "real"));
        doubleOperations.add(new DoubleOperation("*", "real", "integer", "real"));
        doubleOperations.add(new DoubleOperation("/","integer","integer","integer"));
        doubleOperations.add(new DoubleOperation("/","real","real","real"));
        doubleOperations.add(new DoubleOperation("/", "integer", "real", "real"));
        doubleOperations.add(new DoubleOperation("/", "real", "integer", "real"));
        doubleOperations.add(new DoubleOperation(">","integer","integer","boolean"));
        doubleOperations.add(new DoubleOperation(">","real","real","boolean"));
        doubleOperations.add(new DoubleOperation(">", "integer", "real", "boolean"));
        doubleOperations.add(new DoubleOperation(">", "real", "integer", "boolean"));
        doubleOperations.add(new DoubleOperation("<","integer","integer","boolean"));
        doubleOperations.add(new DoubleOperation("<","real","real","boolean"));
        doubleOperations.add(new DoubleOperation("<", "integer", "real", "boolean"));
        doubleOperations.add(new DoubleOperation("<", "real", "integer", "boolean"));
        doubleOperations.add(new DoubleOperation(">=","integer","integer","boolean"));
        doubleOperations.add(new DoubleOperation(">=","real","real","boolean"));
        doubleOperations.add(new DoubleOperation(">=", "integer", "real", "boolean"));
        doubleOperations.add(new DoubleOperation(">=", "real", "integer", "boolean"));
        doubleOperations.add(new DoubleOperation("<=","integer","integer","boolean"));
        doubleOperations.add(new DoubleOperation("<=","real","real","boolean"));
        doubleOperations.add(new DoubleOperation("<=", "integer", "real", "boolean"));
        doubleOperations.add(new DoubleOperation("<=", "real", "integer", "boolean"));
        doubleOperations.add(new DoubleOperation("<>","integer","integer","boolean"));
        doubleOperations.add(new DoubleOperation("<>","real","real","boolean"));
        doubleOperations.add(new DoubleOperation("<>", "integer", "real", "boolean"));
        doubleOperations.add(new DoubleOperation("<>", "real", "integer", "boolean"));
        doubleOperations.add(new DoubleOperation("<>", "boolean", "boolean", "boolean"));
        doubleOperations.add(new DoubleOperation("and", "boolean", "boolean", "boolean"));
        doubleOperations.add(new DoubleOperation("or", "boolean", "boolean", "boolean"));
        doubleOperations.add(new DoubleOperation("and", "integer", "integer", "integer"));
        doubleOperations.add(new DoubleOperation("or", "integer", "integer", "integer"));
        doubleOperations.add(new DoubleOperation("and", "real", "real", "real"));
        doubleOperations.add(new DoubleOperation("or", "real", "real", "real"));
        doubleOperations.add(new DoubleOperation("=","integer","integer","boolean"));
        doubleOperations.add(new DoubleOperation("=","real","real","boolean"));
        doubleOperations.add(new DoubleOperation("=", "integer", "real", "boolean"));
        doubleOperations.add(new DoubleOperation("=", "real", "integer", "boolean"));
        doubleOperations.add(new DoubleOperation("=", "boolean", "boolean", "boolean"));
        stack = new Stack<>();
    }

    public void checkSignAndTypes(List<String> expressions){
        for (String e: expressions){
            getTypeFromExpression(e);
            /*e = new TypeConverter().replaceToType(e);//replaceToType(e);
            List<String> list = new ArrayList<>(Arrays.stream(e.split(" ")).toList());
            //System.out.println(Arrays.toString(list.toArray()));
            Collections.reverse(list);
            Stack<String> expression = new Stack<>();
            expression.addAll(list);
            stack = new Stack<>();
            E(expression);*/
        }
    }

    public String getTypeFromExpression(String expression) {
        String e = new TypeConverter().replaceToType(expression);//replaceToType(e);
        List<String> list = new ArrayList<>(Arrays.stream(e.split(" ")).toList());
        Collections.reverse(list);
        Stack<String> expressionStack = new Stack<>();
        expressionStack.addAll(list);
        stack = new Stack<>();
        E(expressionStack);
        return stack.pop();
    }


    private String replaceToType(String expression){
        String[] ss = expression.split(" ");
        StringBuilder result = new StringBuilder();
        for (String s : ss) {
            result.append(getType(s)).append(" ");
        }
        return result.toString().trim();
    }

    private String getType(String s){
        List<String> onlyIdentifiers = new IdentifierAssignmentChecker().describedWithoutType();

        if(onlyIdentifiers.contains(s)){

            List<String> descriptions = new DescriptionReader().readAll();
            return descriptions.get(onlyIdentifiers.indexOf(s)).split(" ")[1];
        }
        else{
            try {
                Integer.parseInt(s);
                return "integer";
            }
            catch (NumberFormatException e){
                try {
                    Double.parseDouble(s);
                    return "real";
                }
                catch (NumberFormatException e1){
                    if(Boolean.parseBoolean(s)) {
                        return "boolean";
                    }
                    else{
                        return s;
                    }
                }
            }
        }
    }



    private void E(Stack<String> expression) {
        E1(expression);
        while (!expression.empty() &&
                Pattern.matches("<>|=|<|>|>=|<=", expression.peek())) {
            stack.push(expression.pop());
            E1(expression);
            stack.push(getTypeFromOperands(stack));
        }

    }



    private void E1(Stack<String> expression) {
        T(expression);
        while (!expression.empty() && List.of(new String[]{
                "+", "-", "or"}).contains(expression.peek())) {
            stack.push(expression.pop());
            T(expression);
            stack.push(getTypeFromOperands(stack));
        }

    }

    private void T(Stack<String> expression){
        F(expression);
        //if (!expression.empty()){
            while (!expression.empty() && List.of(new String[]{
                    "*","/","and"}).contains(expression.peek())){
                stack.push(expression.pop());
                F(expression);
                stack.push(getTypeFromOperands(stack));;
            }
        //}
    }

    private void F(Stack<String> expression){
        if(!expression.empty()){
            if(expression.peek().equals("not")){
                String s = expression.pop();
                if(!getTypeFromOperands(expression).equals("boolean")){
                    throw new SemanticException("not near not boolean identifier '" + s + "'");
                }
                F(expression);
                //expression.push(s);
            }
            else if(Pattern.matches("(>=|<=|<|>|=|<>)", expression.peek())){
                E(expression);
            }
            else{
                stack.push(expression.pop());
            }
        }

        //stack.push(expression.pop());
    }

    private String getTypeFromOperands(Stack<String> expression){
        String t1,t2,op;
        t1 = expression.pop();
        op = expression.pop();
        t2 = expression.pop();

        for (DoubleOperation d: doubleOperations){
            if(d.getFirsType().equals(t1)
                    && d.getOperation().equals(op)
                    && d.getSecondType().equals(t2)){
                return d.getResultType();
            }
        }
        throw new SemanticException(String.format("'%s %s %s'",t1,op,t2));
    }



}
