package ru.fsm.table;

import ru.fsm.table.util.TableType;
import ru.fsm.table.util.TableUtil;

import java.io.*;

public class TablePutter implements Putter{

    private final BufferedWriter bufferedWriter;
    private final String path;

    public TablePutter(TableType type) throws IOException {
        this.path =getClass().getClassLoader().getResource("source").getPath() + "/"
                + TableUtil.tableFileName(TableType.IDENTIFIER);
        File file = new File(path);
        file.createNewFile();
        this.bufferedWriter = new BufferedWriter(new FileWriter(file));
    }

    @Override
    public int put(String lex) throws IOException {
        bufferedWriter.append(lex);
        bufferedWriter.newLine();
        bufferedWriter.close();
        return (int) new BufferedReader(new FileReader(path)).lines().count();
    }
}
