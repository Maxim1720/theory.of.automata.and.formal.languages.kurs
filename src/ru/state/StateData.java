package ru.state;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

@Getter
@Setter
public class StateData {

    private StateType stateType;
    private String buffer;
    private char ch;

    private InputStreamReader inputStreamReader;
    public StateData(InputStreamReader inputStreamReader){
        this.inputStreamReader = inputStreamReader;
    }


    public char gc(){
        try {
            return (ch = (char) inputStreamReader.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean canRead(){
        try {
            return inputStreamReader.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(){
        buffer += ch;
    }

    public void flush(){
        buffer = "";
    }

    public void put(int t, int z){

    }
}
