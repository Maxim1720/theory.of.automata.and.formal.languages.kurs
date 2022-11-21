package ru.fsm.table.file;

import ru.fsm.table.util.TableType;
import ru.fsm.table.util.TableUtil;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TableWriter {

    private final String path;
    private final BufferedWriter fileWriter;
    private final TableType type;

    public TableWriter(TableType type) throws IOException, URISyntaxException {
        this.type = type;

        /*this.path = getClass()
                .getClassLoader()
                .getResource("out_table") + "/"+ TableUtil.tableFileName(type);*/

        /*this.path = this.getClass()
                .getResource("out_table") + "/"+ TableUtil.tableFileName(type);
*/
        this.path = TableUtil.tableOutPath(type);
        System.out.println(path);

        File file = new File(path);
        file.createNewFile();

        fileWriter = new BufferedWriter(new FileWriter(file));
    }

    public void out(int z) throws IOException {
        fileWriter
                .append(String.valueOf(type.ordinal()+1))
                .append(" ")
                .append(String.valueOf(z));
        fileWriter.newLine();
        fileWriter.close();
    }

}
