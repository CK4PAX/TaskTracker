package jtasktracker;

import jtasktracker.APIJSON;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class APIJSONTest {
    
    public APIJSONTest() {
    }
    @Test
    public void testInitJson() {
        assertEquals(0,APIJSON.initJson());
    }
    @Test
    public void testReadJson() {
        assertNotNull(APIJSON.readJson());
    }
    @Test
    public void testGetListEmpty() {
        String cadena  = "{\"tasks\" : []}";
        List<List> prueba = new ArrayList();
        List<List> object = APIJSON.getList(cadena);
        assertTrue(object.equals(prueba));
    }
    @Test
    public void testGetList() {
        String cadena = "{"
                + "\"tasks\": ["
                + "{\"id\" : 1,"
                + "\"description\" : \"Buy cookies\","
                + "\"status\" : \"todo\","
                + "\"createdAt\" : \"12/02/1923\","
                + "\"updatedAt\" : \"12/02/1924\""
                + "},"
                + "{\"id\" : 2,"
                + "\"description\" : \"wash the dishes\","
                + "\"status\" : \"todo\","
                + "\"createdAt\" : \"01/04/1956\","
                + "\"updatedAt\" : \"15/06/1956\""
                + "}"
                + "]}";
        List<List> lista = new ArrayList<>();
        List a = new ArrayList();
        List b = new ArrayList();
        a.add(1);
        a.add("Buy cookies");
        a.add("todo");
        a.add("12/02/1923");
        a.add("12/02/1924");
        b.add(2);
        b.add("wash the dishes");
        b.add("todo");
        b.add("01/04/1956");
        b.add("15/06/1956");
        lista.add(a);
        lista.add(b);
        assertTrue(lista.equals(APIJSON.getList(cadena)));
    }
    
    @Test
    public void testAddElementToEmptyList() {
        List<List> data = new ArrayList();
        assertDoesNotThrow(()->{APIJSON.addElement(data,"Play guitar");});
    }
    @Test
    public void testAddElementToNoEmptyList() {
        List<List> data = new ArrayList();
        List f_task = new ArrayList();
        f_task.add(5);
        f_task.add("clean the house");
        f_task.add("todo");
        f_task.add(new Date().toString());
        f_task.add(null);
        data.add(f_task);
        assertEquals(6,APIJSON.addElement(data,"Play guitar"));
    }
    
    @Test
    public void testPrepareFormat() {
        List<List> data = new ArrayList();
        List f_task = new ArrayList();
        String date = new Date().toString();
        
        f_task.add(5);
        f_task.add("clean the house");
        f_task.add("todo");
        f_task.add(date);
        f_task.add(null);
        data.add(f_task);
        String test = String.format(""
                + "{\"tasks\" : [{"
                + "\"id\" : 5,"
                + "\"description\" : \"clean the house\","
                + "\"status\" : \"todo\","
                + "\"createdAt\" : \"%s\","
                + "\"updatedAt\" : null"
                + "}]}",date);
        
        assertEquals(test,APIJSON.prepareFormat(data));
    }
    @Test
    public void testPrepareFormatWithEmptyData(){
        List<List> data = new ArrayList();
        String test = "{\"tasks\" : []}";
        assertEquals(test, APIJSON.prepareFormat(data));
    }
    
    @Test
    public void testWriteJson(){
        
        String date = new Date().toString();
        String test = String.format(""
                + "{\"tasks\" : [{"
                + "\"id\" : 5,"
                + "\"description\" : \"clean the house\","
                + "\"status\" : \"todo\","
                + "\"createdAt\" : \"%s\","
                + "\"updatedAt\" : null"
                + "}]}",date);
        assertEquals(0, APIJSON.writeJson(test));
        APIJSON.initJson();
    }
    
    @Test
    public void testFailExistTask(){
        int id = 4;
        List<List> list = new ArrayList<>();
        List f_task = new ArrayList();
        List s_task = new ArrayList();
        String date = new Date().toString();
        
        f_task.add(5);
        f_task.add("clean the house");
        f_task.add("todo");
        f_task.add(date);
        f_task.add(null);
        
        s_task.add(7);
        s_task.add("do the laundry");
        s_task.add("todo");
        s_task.add(date);
        s_task.add(null);
        
        list.add(f_task);
        list.add(s_task);
        
        assertEquals(1, APIJSON.existTask(list,id));
    }
    
    @Test
    public void testExistTask(){
        int id = 5;
        List<List> list = new ArrayList<>();
        List f_task = new ArrayList();
        List s_task = new ArrayList();
        String date = new Date().toString();
        
        f_task.add(5);
        f_task.add("clean the house");
        f_task.add("todo");
        f_task.add(date);
        f_task.add(null);
        
        s_task.add(7);
        s_task.add("do the laundry");
        s_task.add("todo");
        s_task.add(date);
        s_task.add(null);
        
        list.add(f_task);
        list.add(s_task);
        
        assertEquals(0, APIJSON.existTask(list,id));
    }
    
    @Test
    public void testDeleteTask(){
        List<List> list = new ArrayList<>();
        List f_task = new ArrayList();
        List s_task = new ArrayList();
        String date = new Date().toString();
        int id = 5;
        f_task.add(5);
        f_task.add("clean the house");
        f_task.add("todo");
        f_task.add(date);
        f_task.add(null);
        
        s_task.add(7);
        s_task.add("do the laundry");
        s_task.add("todo");
        s_task.add(date);
        s_task.add(null);
        
        list.add(f_task);
        list.add(s_task);
        
        assertEquals(0, APIJSON.deleteTask(list,id));
    }
    
    @Test
    public void testGetTaskFromList(){
        List<List> tasks = new ArrayList<>();
        List task = new ArrayList();
        String date = new Date().toString();
        int id = 5;
        task.add(id);
        task.add("clean the house");
        task.add("todo");
        task.add(date);
        task.add(null);
        tasks.add(task);
        assertEquals(task, APIJSON.getTaskFromList(tasks,id));
        
    }
    
    @Test
    public void testUpdateTask(){
        String first = "play guitar";
        String second = "do code";
        String third = "do the laundry";
        String nuevo = "make experiments";
        List<List> tasks = new ArrayList<>();
        
        APIJSON.addElement(tasks, first);
        int id  = APIJSON.addElement(tasks, second);
        APIJSON.addElement(tasks, third);
        
        APIJSON.updateTask(tasks,id,nuevo);
        
        // Get the updated task then get the attribute description with get()
        assertEquals(nuevo, APIJSON.getTaskFromList(tasks, id).get(1));
        
    }
    
    @Test
    public void testSetUpdatedAtValueInUpdateTask(){
        String old = "do the laundry";
        String nuevo = "make experiments";
        List<List> tasks = new ArrayList<>();
        int id = APIJSON.addElement(tasks, old);
        APIJSON.updateTask(tasks, id, nuevo);
        String updatedAt = (String)APIJSON.getTaskFromList(tasks, id).get(4);
        assertNotNull(updatedAt);
    }
    
    @Test
    public void testSetUpdatedAtValueInMarkInProgress(){
        List<List> tasks = new ArrayList<>();
        String task = "do the laundry";
        int id = APIJSON.addElement(tasks, task);
        APIJSON.markInProgressTask(tasks, id);
        String updatedAt = (String)APIJSON.getTaskFromList(tasks, id).get(4);
        assertNotNull(updatedAt);
    }
    
    @Test
    public void testSetUpdatedAtValueInMarkDone(){
        List<List> tasks = new ArrayList<>();
        String task = "take the garbage";
        int id = APIJSON.addElement(tasks, task);
        APIJSON.markDoneTask(tasks, id);
        String updatedAt = (String)APIJSON.getTaskFromList(tasks, id).get(4);
        assertNotNull(updatedAt);
    }
    
    @Test
    public void testMarkInProgressTask(){
        List<List> tasks = new ArrayList<>();
        int id = APIJSON.addElement(tasks, "play futbol");
        APIJSON.markInProgressTask(tasks,id);
        String status = (String)APIJSON.getTaskFromList(tasks, id).get(2);
        assertEquals("in-progress", status);
    }
    
    @Test
    public void testMarkDoneTask(){
        List<List> tasks = new ArrayList<>();
        int id = APIJSON.addElement(tasks, "do gym");
        APIJSON.markDoneTask(tasks,id);
        String status = (String)APIJSON.getTaskFromList(tasks, id).get(2);
        assertEquals("done", status);
    }
    
    @Test
    public void testGetAdvancedList(){
        String cadena = "{"
                + "\"tasks\": ["
                + "{\"id\" : 1,"
                + "\"description\" : \"Buy cookies\","
                + "\"status\" : \"in-progress\","
                + "\"createdAt\" : \"12/02/1923\","
                + "\"updatedAt\" : \"12/02/1924\""
                + "},"
                + "{\"id\" : 2,"
                + "\"description\" : \"wash the dishes\","
                + "\"status\" : \"todo\","
                + "\"createdAt\" : \"01/04/1956\","
                + "\"updatedAt\" : \"15/06/1956\""
                + "}"
                + "]}";
        List<List> tasks = APIJSON.getList(cadena);
        List<List> test = new ArrayList<>();
        List a = new ArrayList();
        a.add(1);
        a.add("Buy cookies");
        a.add("in-progress");
        a.add("12/02/1923");
        a.add("12/02/1924");
        test.add(a);
        assertTrue(test.equals(APIJSON.getAdvancedList(tasks,"in-progress")));
    }
    
}
