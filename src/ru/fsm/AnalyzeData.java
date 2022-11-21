package ru.fsm;

import ru.fsm.table.TableLooker;
import ru.fsm.table.TableOutter;
import ru.fsm.table.TablePutter;
import ru.fsm.table.search.KnuthMorrisPrattSearcher;
import ru.fsm.table.util.TableType;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.regex.Pattern;

public class AnalyzeData {
    private StateType stateType;
    char ch;
    char[] inputChars;
    String buffer;
    int z, index, wordIndex = 0;

    String[] words;

    public AnalyzeData(String code) {
        //code = code.replaceAll("\\s","");
        words = code.split("[\\s\\t\\n]");
        inputChars = words[wordIndex++].toCharArray();
        buffer = "";
        z = index = 0;
    }

    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public boolean canRead() {
        return index < inputChars.length;
    }

    private enum WordsState{
        CURRENT,
        NEXT,
        END
    }

    public enum InputState{
        EMPTY,
        FILLED
    }



    private WordsState nextWord(){
        if(index >= inputChars.length){

            if(wordIndex >= words.length){
                return WordsState.END;
            }
            index = 0;
            inputChars = words[wordIndex++].toCharArray();
            return WordsState.NEXT;
        }
        return WordsState.CURRENT;
    }
    public char getCh() {
        return ch;
    }

    public void gc() {
        /*
        if (index < inputChars.length) {
            ch = inputChars[index++];
        } else if(wordIndex < words.length) {
            index = 0;
            inputChars = words[wordIndex++].toCharArray();
        }*/
        /*switch (nextWord()){
            case END:{

                break;
            }
            case NEXT:{

            }

        }*/
        if(nextWord().equals(WordsState.END)){
            stateType = StateType.ERR;
            throw new RuntimeException("Invalid lexem");
        }
        ch = inputChars[index++];
    }


    public boolean let(){
        return Pattern.matches("[a-zA-Z]", String.valueOf(ch));
    }

    public boolean digit(){
        return Pattern.matches("\\d",String.valueOf(ch));
    }

    public void flush(){
        buffer = "";
    }

    public void add(){
        buffer += ch;
    }

    public int look(TableType type){
        z = new TableLooker(type).look(buffer);
        return z;
    }

    public void put(TableType type){
        try {
            z = new TablePutter(type).put(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void out(TableType type){
        new TableOutter(type).out(z);
    }


}
