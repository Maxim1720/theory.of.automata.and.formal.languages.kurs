package ru.state.impl;

import ru.file.FileLooker;
import ru.file.FileOuter;
import ru.file.FilePutter;
import ru.state.State;
import ru.state.StateData;
import ru.state.StateType;

import java.util.regex.Pattern;

public class IdentifierState extends State {

    @Override
    public boolean is(String ch) {
        return Pattern.compile("[a-zA-Z]").matcher(ch).matches();
    }

    @Override
    public StateData transit(StateData stateData) {
        stateData.add();
        while (stateData.canRead() && is(String.valueOf(stateData.gc()))){
            stateData.add();
        }
        int z = new FileLooker("/home/almat/IdeaProjects/LexAnalyzer/src/res/source/tw.txt")
                .look(stateData.getBuffer());

        if(z!=0){
            new FileOuter().out(1,z);
        }
        else {
            z = new FileLooker("/home/almat/IdeaProjects/LexAnalyzer/src/res/source/tl.txt")
                    .look(stateData.getBuffer());

            if(z !=0){
                new FileOuter().out(2,z);
            }
            else{
                z = new FileLooker("/home/almat/IdeaProjects/LexAnalyzer/src/res/source/ti.txt")
                        .look(stateData.getBuffer());
                if(z==0){
                    new FilePutter("/home/almat/IdeaProjects/LexAnalyzer/src/res/source/ti.txt")
                            .put(3,stateData.getBuffer());
                }
                new FileOuter().out(3,z);
            }
        }

        stateData.setStateType(StateType.START);
        return stateData;
    }
}
