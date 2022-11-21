package ru.newFsm;

import org.junit.Test;


public class FiniteStatesMachineTest {

    FiniteStatesMachine fsm = new FiniteStatesMachine();
    @Test
    public void testPutChar() {
        fsm.putChar('a');
        fsm.putChar(' ');
        fsm.putChar('\n');
    }
}