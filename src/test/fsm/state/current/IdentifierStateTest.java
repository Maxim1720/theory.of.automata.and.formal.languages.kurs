package fsm.state.current;


import ru.fsm.AnalyzeData;
import ru.fsm.state.current.IdentifierState;
import org.junit.Test;


public class IdentifierStateTest{

    AnalyzeData analyzeData;


    public void beforeInit(){
        analyzeData = new AnalyzeData("integer real for as");
    }

    @Test
    public void testisTransitive() {
        beforeInit();
        analyzeData.gc();
        assert new IdentifierState().isTransitive(analyzeData);
    }

    @Test
    public void testTransit() {
        beforeInit();
        analyzeData.gc();
        IdentifierState identifierState = new IdentifierState();
        while(identifierState.isTransitive(analyzeData))
            analyzeData = identifierState.transit(analyzeData);
    }

    void nextStates() {
        beforeInit();
        analyzeData.gc();
        IdentifierState state = new IdentifierState();
        if(state.isTransitive(analyzeData)){
            state.transit(analyzeData);
        }
        assert state.nextStates() != null;
    }

    void pattern() {

    }
}