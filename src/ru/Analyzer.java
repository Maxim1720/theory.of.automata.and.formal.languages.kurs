package ru;

import ru.file.FileLooker;
import ru.file.FileOuter;
import ru.file.FilePutter;
import ru.state.StateType;

import java.util.regex.Pattern;

public class Analyzer {
    private String buffer;
    private int z;

    private final String sourcePath;


    private final Reader reader;


    public Analyzer() {
        this.z = 0;
        sourcePath = "/home/almat/IdeaProjects/LexAnalyzer/src/res/source/";
        reader = new Reader();
    }


    private boolean canRead(){
        return reader.getCurrent() != Character.MAX_VALUE;
    }


    //если символ был проигнорирован состоянием, его следует сохранить до следующего чтения
    public void analyze(){
        stateType = StateType.START;
        if(reader.charsExists()) {

            while (canRead() && !stateType.equals(StateType.END)) {
                if (reader.getCurrent() == Character.MIN_VALUE) {
                    reader.next();
                }
                flush();
                if (!isWhiteSpace()) {
                    if (let()) {
                        identifierState();
                    } else if (isBinary(reader.getCurrent())) {
                        binaryState();
                    } else if (isOct(reader.getCurrent())) {
                        octState();
                    } else if (isDecimal(reader.getCurrent())) {
                        decimalState();
                    } else if (isComment(reader.getCurrent())) {
                        commentState();
                    } else if (isDelimiter(reader.getCurrent())) {
                        delimiterState();
                    } else {
                        add();
                        throwError();
                    }
                    System.out.println(buffer + "\t" +stateType.name()+ ":" + z);
                } else {
                    stateType = StateType.WHITE_SPACE;
                    reader.next();
                }
            }
        }
        if (!stateType.equals(StateType.END)) {
            throwError();
        }
    }

    private StateType stateType;

    private void biggerCharState() {
        if(canRead()){
            reader.next();
            if(reader.getCurrent()=='='){
                add();
                lookTL(buffer);
                out(2);
            }
        }
    }

    private void lessCharState() {
        if(canRead()){
            reader.next();
            char c = reader.getCurrent();
            if(c == '>' || c == '='){
                add();
                lookTL(buffer);
                out(2);
            }
            System.out.println("less char delimiter: " + buffer);
        }

    }

    private boolean isBiggerChar(char ch) {
        return ch == '>';
    }

    private boolean isLessChar(char ch) {
        return ch == '<';
    }

    private void delimiterState() {
        stateType = StateType.DELIMITER;
        add();
        if (isLessChar(buffer.charAt(0))) {
            lessCharState();
        } else if (isBiggerChar(buffer.charAt(0))) {
            biggerCharState();
        } else if(lookTL(buffer) != 0){
            out(2);
            if(buffer.equals("}")) {
                endState();
            }
        } else {
            throwError();
        }
        reader.next();
    }

    private boolean isDelimiter(char ch) {
        return lookTL(String.valueOf(ch)) != 0;

    }

    private void endState() {
        stateType = StateType.END;
    }

    private void commentState() {
        stateType = StateType.COMMENT;
        boolean end = false;
        while (canRead() && !end){
            reader.next();
            if(reader.getCurrent() == '#'){
                end = true;
                reader.next();
            }
        }
        if(!end){
            throw new IllegalArgumentException("comment wasn't end");
        }
    }

    private boolean isComment(char ch) {
        return ch == '#';
    }


    private void binaryState(){
        stateType = StateType.BINARY;
        while (canRead() && isBinary(reader.getCurrent())){
            add();
            reader.next();
        }
        if(isOct(reader.getCurrent())){
            octState();
        }
        else if(isDecimal(reader.getCurrent())){
            decimalState();
        }
        else if(isFloat(reader.getCurrent())){
            floatState();
        }
        else if(isExp(reader.getCurrent())){
            expState();
        }
        else if(isDecimalEnd(reader.getCurrent())){
            decimalEndState();
        }
        else if(isOctEnd(reader.getCurrent())){
            octEndState();
        }
        else if(isHexFromBinary(reader.getCurrent())){
            hexState();
        }
        else if(isHexEnd(reader.getCurrent())){
            hexEndState();
        }
        else if(isBinaryEnd(reader.getCurrent())){
            binaryEndState();
        }
        else {
            numberEnd();
        }

    }

    private void binaryEndState() {
        add();
        reader.next();
        if(isHex(reader.getCurrent()) || digit()){
            hexState();
        } else if (isHexEnd(reader.getCurrent())) {
            hexEndState();
        } else if (!isWhiteSpace() && !isDelimiter(reader.getCurrent())) {
            throwError();
        }
        else{
            put(4);
            out(4);
        }
    }

    private void hexState() {
        stateType = StateType.HEX;
        while (canRead() && (digit() || isHex(reader.getCurrent()))) {
            add();
            reader.next();
        }
        if (isHexEnd(reader.getCurrent())) {
            hexEndState();
        } else {
            throwError();
        }
    }

    private void hexEndState() {
        stateType = StateType.HEX_END;
        add();
        reader.next();
        if(!isWhiteSpace() && !isDelimiter(reader.getCurrent())){
            add();
            throwError();
        }
    }

    private void octEndState() {
        stateType = StateType.OCT_END;
        add();
        if(canRead()) {
            reader.next();
            if (!isWhiteSpace() && !isDelimiter(reader.getCurrent())) {
                add();
                throwError();
            }
        }
        put(4);
    }

    private void decimalEndState() {
        stateType = StateType.DEC_END;
        add();
        reader.next();
        if(canRead()){
            if(isHex(reader.getCurrent()) || digit()){
                hexState();
            } else if (isHexEnd(reader.getCurrent())) {
                hexEndState();
            }
            else if(!isWhiteSpace()) {
                throwError();
            }
        }
    }

    private void expState() {
        stateType = StateType.EXP;
        add();
        reader.next();
        if (reader.getCurrent() == '+' || reader.getCurrent() == '-' /*|| digit()*/){
            add();
            reader.next();
            while (canRead() && digit()){
                add();
                reader.next();
            }
            if (!isWhiteSpace() && !isDelimiter(reader.getCurrent())){
                throwError();
            }
            numberEnd();
        }
        else if (digit()) {
            /*add();
            reader.next();*/
            while (canRead() && digit()){
                add();
                reader.next();
            }
            if(isHex(reader.getCurrent())){
                hexState();
            }
            else if (isHexEnd(reader.getCurrent())){
                hexEndState();
            }
            else if(!isWhiteSpace() && !isDelimiter(reader.getCurrent())) {
                add();
                throwError();
            }
            else{
                numberEnd();
            }
        }
        else if (isHex(reader.getCurrent())){
            hexState();
        } else if (isHexEnd(reader.getCurrent())) {
            hexEndState();
        }



    }

    private void floatState() {
        stateType = StateType.FLOAT;
        add();
        reader.next();
        boolean existsDigits = false;

        while (canRead() && digit()){
            add();
            reader.next();
            existsDigits = true;
        }
        if (existsDigits){
            if(isExp(reader.getCurrent())){
                floatExpState();
            }
            else if((!isWhiteSpace() && !isDelimiter(reader.getCurrent()))) {
                add();
                throwError();
            }
        }
        else{
            add();
            throwError();
        }
        numberEnd();
    }

    private void numberEnd(){
        put(4);
        out(4);
    }

    private void floatExpState() {
        stateType = StateType.FLOAT_EXP;
        add();
        reader.next();
        if(canRead()){
            if(reader.getCurrent()=='+' || reader.getCurrent()=='-'){
                add();
                reader.next();
                addAllNextDigits();
            } else if (digit()) {
                add();
                addAllNextDigits();
            }
            else {
                throwError();
            }
        }
    }

    private void addAllNextDigits(){
        while (canRead() && digit()){
            add();
            reader.next();
        }
    }

    private boolean isHex(char ch) {
        return Pattern.compile("[ABCDEFabcdef]").matcher(String.valueOf(ch)).matches();
    }

    private void throwError(){
        throw new IllegalArgumentException("invalid lexem: " + buffer);
    }


    private boolean isBinaryEnd(char ch) {
        return Pattern.compile("[Bb]").matcher(String.valueOf(ch)).matches();
    }

    private boolean isHexEnd(char ch) {
        return Pattern.compile("[Hh]").matcher(String.valueOf(ch)).matches();
    }

    private boolean isHexFromBinary(char ch) {
        return Pattern.compile("[ACFacf]").matcher(String.valueOf(ch)).matches();
    }

    private boolean isDecimalEnd(char ch) {
        return Pattern.compile("[Dd]").matcher(String.valueOf(ch)).matches();
    }

    private boolean isExp(char ch) {
        return Pattern.compile("[Ee]").matcher(String.valueOf(ch)).matches();
    }

    private boolean isFloat(char ch) {
        return Pattern.compile("[.]").matcher(String.valueOf(ch)).matches();
    }


    private void decimalState(){
        stateType = StateType.DEC;
        while (canRead() && digit()){
            add();
            reader.next();
        }
        Pattern hexFromDecimal = Pattern.compile("[ABCFabcf]");

        if(hexFromDecimal.matcher(String.valueOf(reader.getCurrent())).matches()){
            hexState();
        } else if (isFloat(reader.getCurrent())) {
            floatState();
        } else if (isExp(reader.getCurrent())) {
            expState();
        } else if (isHexEnd(reader.getCurrent())){
            hexEndState();
        }
        else if (isDecimalEnd(reader.getCurrent())){
            decimalEndState();
        }
        else if(!isWhiteSpace() && !isDelimiter(reader.getCurrent())) {
            add();
            throwError();
        }
        else {
            numberEnd();
        }
        /*put(4);
        out(4);*/
    }
    private boolean isDecimal(char c){
        return Pattern.compile("[89]").matcher(String.valueOf(c)).matches();
    }

    private void octState(){
        stateType = StateType.OCT;
        Pattern pattern = Pattern.compile("[0-7]");
        while (canRead() && pattern.matcher(String.valueOf(reader.getCurrent())).matches()){
            add();
            reader.next();
        }
        if (isOctEnd(reader.getCurrent())){
            octEndState();
            System.out.println("8cc " + buffer);
        } else if (isDecimal(reader.getCurrent())) {
            decimalState();
        } else if (isExp(reader.getCurrent())) {
            expState();
        } else if (isDecimalEnd(reader.getCurrent())) {
            decimalEndState();
        }
        //hex from oct
        else if (Pattern.compile("[ABCF]").matcher(String.valueOf(reader.getCurrent())).matches()) {
            hexState();
        } else if (isHexEnd(reader.getCurrent())) {
            hexEndState();
        } else if (isFloat(reader.getCurrent())) {
            floatState();
        } else if (!isWhiteSpace() && !isDelimiter(reader.getCurrent())){
            add();
            throwError();
        }
        else {
            numberEnd();
        }
    }



    private boolean isOctEnd(char c){
        return Pattern.compile("[Oo]").matcher(String.valueOf(c)).matches();
    }

    private boolean isOct(char c){
        return Pattern.compile("[2-7]").matcher(String.valueOf(c)).matches();
    }
    private boolean isBinary(char c){
        return Pattern.compile("[01]").matcher(String.valueOf(c)).matches();
    }
    private void identifierState(){
        stateType = StateType.IDENTIFIER;
        while (canRead() && isIdentifier()){
            add();
            reader.next();
        }
        if(lookTW(buffer)!=0){
            out(1);
        }else {
            if(lookTL(buffer) !=0){
                out(2);
            }
            else{
                if(lookTI(buffer)==0){
                    put(3);
                }
                out(3);
            }
        }
        //reader.next();
        //gc();

        //System.out.println("identifier: " + buffer);
    }

    private int lookTI(String lexem) {
        z = new FileLooker(sourcePath + "ti.txt").look(lexem);
        return z;
    }

    private void flush(){
        buffer = "";
    }

    private void put(int t){
        String[] srsNames = new String[]{
                "w","l","i","n"
        };
        String path = sourcePath+"t"+srsNames[t-1]+".txt";
        z = new FilePutter(path).put(t, buffer);
    }
    private int lookTL(String lexem){
        z = new FileLooker(sourcePath+"tl.txt").look(lexem);
        return z;
    }
    private void out(int t){
        new FileOuter().out(t,z);
    }
    private int lookTW(String lexem) {
        String path = "/home/almat/IdeaProjects/LexAnalyzer/src/res/source/tw.txt";
        z = new FileLooker(path).look(lexem);
        return z;

    }
    private boolean isWhiteSpace(){
        return Character.isWhitespace(reader.getCurrent());
    }

    private boolean isIdentifier(){
        return let() || digit();
    }



    private void add(){
        buffer+=reader.getCurrent();
    }

    private boolean let(){
        return Character.isLetter(reader.getCurrent());//Pattern.compile("[a-zA-Z]").matcher(String.valueOf(reader.getCurrent())).matches();
    }

    private boolean digit(){
        return Character.isDigit(reader.getCurrent());//Pattern.compile("\\d").matcher(String.valueOf(reader.getCurrent())).matches();
    }
}
