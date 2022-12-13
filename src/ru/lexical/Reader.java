package ru.lexical;

import ru.lexical.file.FileLooker;
import ru.lexical.file.TableUtil;
import ru.lexical.state.StateType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public final class Reader {

    private final BufferedReader bufferedReader;
    private char ch;

    private String buffer;

    private StateType stateType;

    public Reader() {
        try {
            String codePath = "/home/almat/IdeaProjects/LexAnalyzer/src/res/code.txt";
            bufferedReader = new BufferedReader(new FileReader(Paths.get(
                    codePath).toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        flush();
        next();
    }

    public String getBuffer(){
        return buffer;
    }

    public void flush(){
        buffer = "";
    }
    public void add(){
        buffer+=getCurrent();
    }

    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public boolean charsExists()  {
        try {
            return bufferedReader.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public char getCurrent(){
        return ch;
    }

    public void next(){
        ch = gc();
    }
    private char gc(){
        try {
            int c = bufferedReader.read();
            if(c == -1){
                c = Character.MAX_VALUE;
            }
            return (char) c;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean currentIsDelimiter() {
        return new FileLooker(TableUtil.tlPath).look(String.valueOf(getCurrent())) != 0;
    }

}
