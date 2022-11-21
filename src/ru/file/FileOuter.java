package ru.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOuter {

    private final String outPath;

    public FileOuter(){
        this.outPath =
                "/home/almat/IdeaProjects/LexAnalyzer/" +
                        "src/res/out_table/lex_table.txt";
    }

    public void out(int t, int z){
        try {
            File file = new File(outPath);
            if(!file.exists()){
                file.createNewFile();
            }
            try (FileWriter fileWriter = new FileWriter(file,true)){
                fileWriter.append(String.format("%d %d", t, z));
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
