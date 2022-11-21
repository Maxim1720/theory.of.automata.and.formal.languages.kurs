package ru.fsm.table;

import ru.fsm.table.file.TableWriter;
import ru.fsm.table.util.TableType;

import java.io.IOException;
import java.net.URISyntaxException;

public class TableOutter implements Outter{
    private final TableType type;

    public TableOutter(TableType type){
        this.type = type;
    }

    @Override
    public void out(int z) {
        try {
            new TableWriter(type).out(z);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
