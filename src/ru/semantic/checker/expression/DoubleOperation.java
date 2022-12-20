package ru.semantic.checker.expression;

public class DoubleOperation {
    private String operation;
    private String firsType;
    private String secondType;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFirsType() {
        return firsType;
    }

    public void setFirsType(String firsType) {
        this.firsType = firsType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    private String resultType;

    public DoubleOperation(String operation, String firsType, String secondType, String resultType) {
        this.operation = operation;
        this.firsType = firsType;
        this.secondType = secondType;
        this.resultType = resultType;
    }


}
