package ru.fsm.table.util;

public class TableUtil {

    private static String[] names = new String[]{"w","l","i","n"};

    public static String tableFileName(TableType type){
        return "t"+names[type.ordinal()]+".txt";
    }

    public static String tableSrcPath(TableType type){
        return /*"/src/res/source/t"*/"/home/almat/IdeaProjects/LexAnalyzer/src/res/source/t" + names[type.ordinal()]+".txt";
    }

    public static String tableOutPath(TableType type){
        return /*"/src/res/out_table/t"*/"/home/almat/IdeaProjects/LexAnalyzer/src/res/out_table/t" + names[type.ordinal()] + ".txt";
    }

}
