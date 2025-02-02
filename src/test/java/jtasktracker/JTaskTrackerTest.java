package jtasktracker;

import java.util.ArrayList;
import java.util.List;
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
    public void testPassValidateSecondArgOfListCase(){
        String name = "done";
        assertEquals(0, JTaskTracker.ValidateSecondArgOfListCase(name));
    }
    
    @Test
    public void testFailValidateSecondArgOfListCase(){
        String name = "in progress";
        assertEquals(1, JTaskTracker.ValidateSecondArgOfListCase(name));
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
    public void testPassValidateQuantityOfArgumentsForThree() {
        String[] args = {"update","2","buy milk"};
        assertEquals(0,JTaskTracker.validateQuantityOfArgumentsForThree(args));
    }
    
    @Test
    public void testFailValidateQuantityOfArgumentsForThree() {
        String[] args = {"update","2","buy milk","done"};
        assertEquals(1,JTaskTracker.validateQuantityOfArgumentsForThree(args));
    }
    
    @Test
    public void testFailValidateQuantityOfArgumentsForOne() {
        String[] args = {"list","todo"};
        assertEquals(1,JTaskTracker.validateQuantityOfArgumentsForOne(args));
    }
    @Test
    public void testPassValidateQuantityOfArgumentsForOne() {
        String[] args = {"list"};
        assertEquals(0,JTaskTracker.validateQuantityOfArgumentsForOne(args));
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
        assertDoesNotThrow(()->{JTaskTracker.addTask(args);});
        APIJSON.initJson();
    }
    @Test
    public void testExistTask() {
        String[] task = {"add","work in the project"};
        String id = Integer.toString(JTaskTracker.addTask(task));
        assertEquals(0,JTaskTracker.existTask(id));
        JTaskTracker.deleteTask(new String[]{"delete",id});
    }
    @Test
    public void testDeleteTask() {
        String[] task = {"add","work in the project"};
        int id = JTaskTracker.addTask(task);
        String[] args = {"delete",Integer.toString(id)};
        JTaskTracker.deleteTask(args);
        assertEquals(1,JTaskTracker.existTask(args[1]));
    }
    
    @Test
    public void testUpdateTask(){
        String[] first = {"add","practice swimming"}; 
        String[] second = {"add","take mandarin class"}; 
        
        int f_id = JTaskTracker.addTask(first);
        int s_id = JTaskTracker.addTask(second);
        
        String[] args = {"update",Integer.toString(s_id),"take surf class"}; 
        int result = JTaskTracker.updateTask(args);
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(f_id)});
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(s_id)});
        assertEquals(0, result);

    }
    
    @Test 
    public void testGetFormattedTasks(){
        List<List> tasks = new ArrayList<>();
        APIJSON.addElement(tasks, "run 10 km");
        assertEquals(0, JTaskTracker.getFormattedTasks(tasks));
    }
    
    @Test
    public void testGetList(){
        String[] f_task = {"add","do exercises"};
        String[] s_task = {"add","brush teeth"};
        
        int f_id = JTaskTracker.addTask(f_task);
        int s_id = JTaskTracker.addTask(s_task);
        
        assertEquals(0, JTaskTracker.getList());
        
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(f_id)});
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(s_id)});
    }
    
    @Test
    public void testMarkInProgress(){
        String[] f_task = {"add","do exercises"};
        String[] s_task = {"add","brush teeth"};
        
        int f_id = JTaskTracker.addTask(f_task);
        int s_id = JTaskTracker.addTask(s_task);
        
        String[] args = {"mark-in-progress",Integer.toString(s_id)};
        
        assertEquals(0, JTaskTracker.markInProgress(args));
        
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(f_id)});
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(s_id)});
    }
    
    @Test
    public void testGetAdvancedList(){
        String[] f_task = {"add","do exercises"};
        String[] s_task = {"add","brush teeth"};
        
        int f_id = JTaskTracker.addTask(f_task);
        int s_id = JTaskTracker.addTask(s_task);
        
        String[] args = {"list","todo"};
        
        assertEquals(0, JTaskTracker.getAdvancedList(args));
        
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(f_id)});
        JTaskTracker.deleteTask(new String[]{"delete",Integer.toString(s_id)});
    }
    
    
    
}



















