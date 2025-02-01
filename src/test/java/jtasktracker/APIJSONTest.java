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
        assertEquals(0,APIJSON.addElement(data,"Play guitar"));
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
        assertEquals(0,APIJSON.addElement(data,"Play guitar"));
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
}
