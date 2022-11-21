package ru.file;

import java.io.*;

public class FilePutter {

    private final String path;

    public FilePutter(String path){
        this.path = path;
    }


    public int put(int t, String buffer){
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }

            try(FileWriter fileWriter = new FileWriter(file,true)){
                fileWriter.append(buffer);
                fileWriter.append("\n");
            }
            return (int) new BufferedReader(new FileReader(file)).lines().count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
