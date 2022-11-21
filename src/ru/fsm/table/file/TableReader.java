package ru.fsm.table.file;

import ru.fsm.table.util.TableType;
import ru.fsm.table.util.TableUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TableReader {

    private final BufferedReader bufferedReader;

    public TableReader(TableType type) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(
                TableUtil.tableSrcPath(type)
        ));
    }

    public List<String> lines(){
        return bufferedReader.lines().toList();
    }

    public int getIndex(String lex){
        return lines().indexOf(lex) + 1;
    }
}
