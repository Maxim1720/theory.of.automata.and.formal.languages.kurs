package ru.util;

import ru.semantic.checker.IdentifierAssignmentChecker;
import ru.semantic.reader.DescriptionReader;

import java.util.List;

public class TypeConverter {

    public String replaceToType(String expression){
        String[] ss = expression.split(" ");
        StringBuilder result = new StringBuilder();
        for (String s : ss) {
            result.append(getType(s)).append(" ");
        }
        return result.toString().trim();
    }

    public String getType(String s){
        List<String> onlyIdentifiers = new IdentifierAssignmentChecker().describedWithoutType();

        if(onlyIdentifiers.contains(s)){

            List<String> descriptions = new DescriptionReader().readAll();
            return descriptions.get(onlyIdentifiers.indexOf(s)).split(" ")[1];
        }
        else{
            try {
                Integer.parseInt(s);
                return "integer";
            }
            catch (NumberFormatException e){
                try {
                    Double.parseDouble(s);
                    return "real";
                }
                catch (NumberFormatException e1){
                    if(Boolean.parseBoolean(s)) {
                        return "boolean";
                    }
                    else{
                        return s;
                    }
                }
            }
        }
    }

}
