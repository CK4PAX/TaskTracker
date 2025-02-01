package jtasktracker;

import jtasktracker.JTaskTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JTaskTrackerTest {
    
    public JTaskTrackerTest() {
    }

    @Test
    public void testEmptyArgumentsWithArgs() {
        String[] args = {"add","buy milk"};
        assertEquals(0,JTaskTracker.emptyArguments(args));
    }
    @Test
    public void testEmptyArgumentsNoArgs() {
        String[] args = {};
        assertEquals(1,JTaskTracker.emptyArguments(args));
    }
    @Test
    public void testFailFirstArguments() {
        String[] args = {"agregar","buy milk"};
        assertDoesNotThrow(()->{JTaskTracker.main(args);});
    }
    @Test
    public void testFailValidateQuantityOfArguments() {
        String[] args = {"add","buy milk","56","done"};
        int n_args = 2;
        assertEquals(1,JTaskTracker.validateQuantityOfArguments(args,n_args));
    }
    @Test
    public void testPassValidateQuantityOfArguments() {
        String[] args = {"add","buy milk","56","done"};
        int n_args = 4;
        assertEquals(0,JTaskTracker.validateQuantityOfArguments(args,n_args));
    }
    @Test
    public void testFailValidateQuantityOfArgumentsForTwo() {
        String[] args = {"add","buy milk","56"};
        assertEquals(1,JTaskTracker.validateQuantityOfArgumentsForTwo(args));
    }
    @Test
    public void testPassValidateQuantityOfArgumentsForTwo() {
        String[] args = {"add","buy milk"};
        assertEquals(0,JTaskTracker.validateQuantityOfArgumentsForTwo(args));
    }
    @Test
    public void testFailValidateTypeOfSecondArg() {
        String[] args = {"add","two"};
        assertEquals(1,JTaskTracker.validateTypeOfSecondArg(args));
    }
    @Test
    public void testPassValidateTypeOfSecondArg() {
        String[] args = {"add","2"};
        assertEquals(0,JTaskTracker.validateTypeOfSecondArg(args));
    }
    @Test
    public void testAddTask() {
        String[] args = {"add","clean the house"};
        assertEquals(0,JTaskTracker.addTask(args));
    }
}
