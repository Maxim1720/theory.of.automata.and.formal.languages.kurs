package ru.syntactical.state;

import ru.exception.syn.SyntacticException;
import ru.semantic.reader.DescriptionReader;
import ru.syntactical.State;
import ru.syntactical.reader.LexemeReader;
import ru.syntactical.writer.DescriptionOuter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Description implements State {

    List<String> types;

    DescriptionOuter descriptionOuter;
    private final List<String> identifiers;
    private String type;
    public Description(String firstIdentifier) {
        types = new ArrayList<>();
        types.addAll(List.of("boolean", "integer", "real"));

        identifiers = new ArrayList<>();
        descriptionOuter = new DescriptionOuter();

        identifiers.add(firstIdentifier);
    }

    @Override
    public LexemeReader transit(LexemeReader lexemeReader) {
        while (lexemeReader.eq(",")) {
            lexemeReader.next();
            if (!lexemeReader.identifier()) {
                throw new SyntacticException(lexemeReader.current(), "IDENTIFIER");
            }
            identifiers.add(lexemeReader.current());
            lexemeReader.next();
        }
        if (lexemeReader.eq(":")) {
            lexemeReader.next();
            if (types.contains(lexemeReader.current())) {
                type = lexemeReader.current();
                lexemeReader.next();
            } else {
                throw new SyntacticException(lexemeReader.current(), Arrays.toString(types.toArray()));
            }
        } else {
            throw new SyntacticException(lexemeReader.current(), ":");
        }
        writeDescription();
        return lexemeReader;
    }

    @Override
    public boolean is(LexemeReader lexemeReader) {
        return lexemeReader.identifier();
    }


    private void writeDescription(){
        for (String s: identifiers){
            descriptionOuter.out(s + " " + type);
        }
    }
}
