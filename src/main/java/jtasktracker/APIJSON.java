package jtasktracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class APIJSON {

    final static Path PATH = Path.of("src/resources/data/data.json");

    public static int initJson() {
        String init = "{\"tasks\" : []}";

        try {
            Files.writeString(PATH, init, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return 1;
        }
        return 0;
    }

    public static String readJson() {
        if (!Files.exists(PATH)) {
            initJson();
        }
        try {
            return Files.readString(PATH, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static List getAttr(List attributes, String fragment) {
        List attr = attributes;
        if (Pattern.matches("\".*\"", fragment)) {
            attr.add(fragment.substring(1, fragment.length() - 1));
        } else if (Pattern.matches("\\d+", fragment)) {
            attr.add(Integer.parseInt(fragment));
        } else if (fragment.equals("null")) {
            attr.add(null);
        }
        return attr;

    }

    public static List getTask(String data, int o_brace, int c_brace) {
        int colon = data.indexOf(":", o_brace, c_brace) + 1;
        int aux = data.indexOf(",", o_brace, c_brace);
        int coma = (aux != -1) ? aux : c_brace;
        List task = new ArrayList();
        while (coma < c_brace) {
            String fragment = data.substring(colon, coma).strip();
            task = getAttr(task, fragment);
            colon = data.indexOf(":", coma + 1, c_brace) + 1;
            aux = data.indexOf(",", coma + 1, c_brace);
            coma = (aux != -1) ? aux : c_brace;
        }
        String fragment = data.substring(colon, coma).strip();
        task = getAttr(task, fragment);
        return task;
    }

    public static List<List> getTasks(String data, int o_sq_bracket, int c_sq_bracket) {
        List<List> tasks = new ArrayList<>();
        int o_brace = data.indexOf("{", o_sq_bracket, c_sq_bracket);
        int c_brace = data.indexOf("}", o_sq_bracket, c_sq_bracket);
        while (o_brace != -1) {
            List task = getTask(data, o_brace, c_brace);
            tasks.add(task);
            o_brace = data.indexOf("{", c_brace, c_sq_bracket);
            c_brace = data.indexOf("}", c_brace + 1, c_sq_bracket);
        }
        return tasks;
    }
    public static List<List> getList(String data) {
        int o_bracket = data.indexOf("[");
        int c_bracket = data.lastIndexOf("]");
        List<List> tasks = getTasks(data, o_bracket, c_bracket);
        return tasks;
    }
    /*
    public static List<List> getList(String lista) {

        List<List> data = new ArrayList<>();
        // Save index of square brackets
        int o_bracket = lista.indexOf("[");
        int c_bracket = lista.lastIndexOf("]");

        int o_brace = lista.indexOf("{", o_bracket, c_bracket);
        int c_brace = lista.indexOf("}", o_bracket, c_bracket);

        do {
            if (o_brace != -1) {
                List task = new ArrayList<>();
                int colon = lista.indexOf(":", o_brace, c_brace) + 1;
                int aux = lista.indexOf(",", o_brace, c_brace);
                int coma = (aux != -1) ? aux : c_brace;
                do {
                    if (colon != -1) {
                        String fragment = lista.substring(colon, coma).strip();
                        if (Pattern.matches("\".*\"", fragment)) {
                            task.add(fragment.substring(1, fragment.length() - 1));
                        } else if (Pattern.matches("\\d*", fragment)) {
                            task.add(Integer.parseInt(fragment));
                        } else if (fragment.equals("null")) {
                            task.add(null);
                        }
                        colon = lista.indexOf(":", coma + 1, c_brace) + 1;
                        aux = lista.indexOf(",", coma + 1, c_brace);
                        coma = (aux != -1) ? aux : c_brace;
                    }
                } while (coma < c_brace);
                String fragment = lista.substring(colon, coma).strip();
                if (fragment.equals("null")) {
                    task.add(null);
                } else {
                    task.add(fragment.substring(1, fragment.length() - 1));
                }

                o_brace = lista.indexOf("{", c_brace, c_bracket);
                c_brace = lista.indexOf("}", c_brace + 1, c_bracket);
                data.add(task);
            }
        } while (o_brace != -1);
        return data;
    }*/

    public static int addElement(List<List> data, String task) {
        List new_task = new ArrayList();

        if (data.isEmpty()) {
            new_task.add(1);

        } else {
            // set id getting the last id of a no-empty list
            new_task.add((int) data.getLast().get(0) + 1);
        }
        new_task.add(task);
        new_task.add("todo");
        new_task.add(new Date().toString());
        new_task.add(null);

        data.add(new_task);
        return 0;
    }

    public static String prepareFormat(List<List> data) {
        if (data.isEmpty()) {
            return "{\"tasks\" : []}";
        }
        String tasks = "";
        String base = "";

        for (List list : data) {
            String task = String.format(""
                    + ",{\"id\" : %d,"
                    + "\"description\" : \"%s\","
                    + "\"status\" : \"%s\","
                    + "\"createdAt\" : \"%s\","
                    + "\"updatedAt\" : ",
                    list.get(0), list.get(1),
                    list.get(2), list.get(3));

            // Check if value of updatedAt is null or not
            if (list.get(4) == null) {
                task = task.concat(String.format("%s}", list.get(4)));
            } else {
                task = task.concat(String.format("\"%s\"}", list.get(4)));
            }
            tasks = tasks.concat(task);
        }
        tasks = tasks.substring(1);
        base = String.format("{\"tasks\" : [%s]}", tasks);
        return base;
    }

    public static int writeJson(String data) {
        try {
            Files.writeString(PATH, data, StandardCharsets.UTF_8);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

}
