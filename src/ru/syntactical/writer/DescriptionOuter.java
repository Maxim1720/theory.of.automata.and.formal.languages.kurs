package ru.syntactical.writer;

import ru.util.TableUtil;

import java.io.*;

public class DescriptionOuter {

    public void out(String lexeme) {
        String path = TableUtil.descriptions;
        File file = new File(path);

        /*final int[] count = {0};
        List<String> descriptions = new DescriptionReader().readAll();

        descriptions.forEach(s -> {
            if(s.equals(lexeme)){
                count[0]++;
            }
        });*/

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            if(file.length() != 0){
                bufferedWriter.newLine();
            }
            bufferedWriter.append(lexeme);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
