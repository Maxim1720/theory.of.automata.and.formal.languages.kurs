package ru.syntactical.reader;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class LexemeReaderImplTest {

    @Test
    public void testIdentifierRegex(){

        assert Pattern.matches("[a-zA-Z][a-zA-Z\\d]*", "a");

    }


    @org.junit.Test
    public void test(){
        String text = "123";

        Stack<Character> stack = new Stack<>();
        stack.addAll(List.of('1','2','3'));

        while (!stack.empty()){
            System.out.println(stack.peek());
            stack.pop();
        }
    }

    @org.junit.Test
    public void next() {
        LexemeReaderImpl lexemeReader = new LexemeReaderImpl();
        assert lexemeReader.next();
    }

    @org.junit.Test
    public void eq() {
        LexemeReaderImpl lexemeReader = new LexemeReaderImpl();
        String s = "{";
        assert lexemeReader.eq(s);
        assert lexemeReader.next();
        assert lexemeReader.eq("a");
    }

    @org.junit.Test
    public void identifier() {
        LexemeReaderImpl lexemeReader = new LexemeReaderImpl();
        lexemeReader.eq("{");
        lexemeReader.next();
        assert lexemeReader.identifier();
    }

    @org.junit.Test
    public void number() {
        LexemeReaderImpl lexemeReader = new LexemeReaderImpl();
        for (int i=0;i<17;i++){
            assert lexemeReader.next();
        }
        assert lexemeReader.number();
    }
}