package ru.fsm.table;

import ru.fsm.table.file.TableReader;
import ru.fsm.table.util.TableType;

import java.io.FileNotFoundException;

public class TableLooker implements Looker {

    private final TableType type;

    public TableLooker(TableType type) {
        this.type = type;
    }

    @Override
    public int look(String lex) {
        try {
            return new TableReader(type).getIndex(lex);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
