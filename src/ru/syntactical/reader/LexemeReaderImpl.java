package ru.syntactical.reader;

import ru.lexical.file.FileLooker;
import ru.util.TableUtil;

import java.io.*;

public class LexemeReaderImpl implements LexemeReader{
    private String lexeme;
    private final BufferedReader bufferedReader;

    public LexemeReaderImpl(){
        File file = new File(TableUtil.lexemes);
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        next();
    }

    @Override
    public boolean next() {
        try {
            if(bufferedReader.ready()){
                lexeme = bufferedReader.readLine();
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eq(String s) {
        return s.equals(lexeme);
    }

    @Override
    public boolean identifier() {
        return new FileLooker(TableUtil.tiPath).look(lexeme)!=0;
    }

    @Override
    public boolean number() {
        return new FileLooker(TableUtil.tnPath).look(lexeme)!=0;
    }

    @Override
    public String current() {
        return lexeme;
    }
}
