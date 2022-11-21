package ru;

import ru.file.FileLooker;
import ru.file.FileOuter;
import ru.file.FilePutter;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Analyzer {

    private char ch;

    private String buffer;
    private int index, z;
    private final char[] code;

    private final BufferedReader codeReader;

    private final String outPath;
    private final String sourcePath;

    public Analyzer(String code){
        this.code = code.toCharArray();
        this.index = 0;
        this.z = 0;

        try {
            codeReader = new BufferedReader(new FileReader(Paths.get(
                    "/home/almat/IdeaProjects/LexAnalyzer/src/res/code.txt"
            ).toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        outPath = "/home/almat/IdeaProjects/LexAnalyzer/src/res/out_table/lex_table.txt";
        sourcePath = "/home/almat/IdeaProjects/LexAnalyzer/src/res/source/";

    }


    public void analyze(){
        while (gc()){
            flush();
            if(!isWhiteSpace()){
//                add();
                if(let()) {
//                    add();
                    identifierState();
                }
                else if(isBinary(ch)){
                    binaryState();
                }
                else if(isOct(ch)){
                    octState();
                }
                else if (isDecimal(ch)){
                    decimalState();
                } else if(isComment(ch)){
                    commentState();
                } else if (isDelimiter(ch)) {
                    delimiterState();
                }
                else {
                    add();
                    throwError();
                }
            }
            System.out.println(buffer);
        }
    }

    private void biggerCharState() {
        if(gc()){
            if(ch=='='){
                add();
                out(3);
            }
        }
    }

    private void lessCharState() {

        if(gc()){
            if(ch == '>'){
                add();
                lookTL(buffer);
                out(3);


            }
            else if (ch == '='){
                add();
                lookTL(buffer);
                out(3);
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
        add();
        if (isLessChar(ch)) {
            lessCharState();
        } else if (isBiggerChar(ch)) {
            biggerCharState();
        } else if(lookTL(buffer) != 0){
            out(2);
            if(ch == '}') {
                endState();
            }
        } else {
            throwError();
        }

    }

    private boolean isDelimiter(char ch) {
        return lookTL(String.valueOf(ch)) != 0;

    }

    private void endState() {

    }

    private void commentState() {
        boolean end = false;
        while (gc()){
            if(ch == '#'){
                end = true;
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
        add();
        while (gc() && isBinary(ch)){
            add();
        }
        if(isOct(ch)){
            octState();
        }
        else if(isDecimal(ch)){
            decimalState();
        }
        else if(isFloat(ch)){
            floatState();
        }
        else if(isExp(ch)){
            expState();
        }
        else if(isDecimalEnd(ch)){
            decimalEndState();
        }
        else if(isOctEnd(ch)){
            octEndState();
        }
        else if(isHexFromBinary(ch)){
            hexState();
        }
        else if(isHexEnd(ch)){
            hexEndState();
        }
        else if(isBinaryEnd(ch)){
            binaryEndState();
            System.out.println("2cc " + buffer);
        }
        else {
            System.out.println("10cc " + buffer);
            numberEnd();
        }

    }

    private void binaryEndState() {
        add();
        if(isHex(ch) || digit()){
            hexState();
        } else if (isHexEnd(ch)) {
            hexEndState();
        } else if (gc() && !isWhiteSpace()) {
            throwError();
        }
        else{
            put(4);
            out(4);
        }
    }

    private void hexState() {

        while (gc() && (digit() || isHex(ch))){
            add();
        }
        if(isHexEnd(ch)){
            hexEndState();
        }
        else {//(!isWhiteSpace() && lookTL(String.valueOf(ch))==0){
            add();
            throwError();
        }

    }

    private void hexEndState() {
        add();
        put(4);
        out(4);
    }

    private void octEndState() {
        add();
        if(gc() && !isWhiteSpace()){
            throwError();
        }
    }

    private void decimalEndState() {
        add();
        if(gc()){
            if(isHex(ch) || digit()){
                hexState();
            } else if (isHexEnd(ch)) {
                hexEndState();
            }
            else if(!isWhiteSpace()) {
                throwError();
            }
        }
        System.out.println("10cc " + buffer);


    }

    private void expState() {
        add();
        gc();
        if (ch == '+' || ch == '-'){
            add();
            while (gc() && digit()){
                add();
            }
            if (!isWhiteSpace()){
                throwError();
            }
            put(4);
            out(4);
        }
        else if (digit()) {
            add();
            while (gc() && digit()){
                add();
            }
            if(isHex(ch)){
                hexState();
            }
            else if (isHexEnd(ch)){
                hexEndState();
            }
            else if(!isWhiteSpace()) {
                throwError();
            }
            System.out.println("exp: " + buffer);
        }
        else if (isHex(ch)){
            hexState();
        } else if (isHexEnd(ch)) {
            hexEndState();
        }
    }

    private void floatState() {
        add();
        while (gc() && digit()){
            add();
        }
        if(isExp(ch)){
            floatExpState();
        }else if(!isWhiteSpace() && lookTL(String.valueOf(ch))==0) {
            add();
            throwError();
        }
        numberEnd();
        System.out.println("float: " + buffer);
    }

    private void numberEnd(){
        put(4);
        out(4);
    }

    private void floatExpState() {
        add();
        if(gc()){
            if(ch=='+' || ch=='-'){
                add();
                addAllNextDigits();
            } else if (digit()) {
                add();
                addAllNextDigits();
            }
            else {
                throwError();
            }
            System.out.println("float exp: " + buffer);
        }
    }

    private void addAllNextDigits(){
        while (gc() && digit()){
            add();
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
        add();
        while (gc() && digit()){
            add();
        }
        Pattern hexFromDecimal = Pattern.compile("[ABCFabcf]");
        if(hexFromDecimal.matcher(String.valueOf(ch)).matches()){
            hexState();
        } else if (isFloat(ch)) {
            floatState();
        } else if (isExp(ch)) {
            expState();
        } else if (isHexEnd(ch)){
            hexEndState();
        }
        else if (isDecimalEnd(ch)){
            decimalEndState();
        }
        else{
            System.out.println("10cc " + buffer);
        }
        put(4);
        out(4);
    }
    private boolean isDecimal(char c){
        return Pattern.compile("[89]").matcher(String.valueOf(c)).matches();
    }

    private void octState(){
        add();
        Pattern pattern = Pattern.compile("[0-7]");
        while (gc() && pattern.matcher(String.valueOf(ch)).matches()){
            add();
        }
        if (isOctEnd(ch)){
            octEndState();
            System.out.println("8cc " + buffer);
        } else if (isDecimal(ch)) {
            decimalState();
        } else if (isExp(ch)) {
            expState();
        } else if (isDecimalEnd(ch)) {
            decimalEndState();
        }
        //hex from oct
        else if (Pattern.compile("[ABCF]").matcher(String.valueOf(ch)).matches()) {
            hexState();
        } else if (isHexEnd(ch)) {
            hexEndState();
        } else if (isFloat(ch)) {
            floatState();
        } else if (!isWhiteSpace() && lookTL(String.valueOf(ch))==0){
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
        add();
        while (gc() && isIdentifier(ch)){
            add();
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
        System.out.println("identifier: " + buffer);
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
        return Pattern.matches("\\s",String.valueOf(ch));
    }

    private boolean isIdentifier(char c){
        return let() || digit();
    }

    private boolean gc(){
        try {
            if(codeReader.ready()){
                ch = (char)codeReader.read();
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void add(){
        buffer+=ch;
    }

    private boolean let(){
        return Pattern.compile("[a-zA-Z]").matcher(String.valueOf(ch)).matches();
    }

    private boolean digit(){
        return Pattern.compile("\\d").matcher(String.valueOf(ch)).matches();
    }
}
