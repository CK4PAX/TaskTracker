package jtasktracker;

import static jtasktracker.APIJSON.PATH;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JTaskTracker {

    public static int emptyArguments(String[] args){
        if(args.length == 0){
            return 1;
        }
        return 0;
    }
    public static int validateQuantityOfArguments(String[] args, int n){
        if(args.length != n){
            return 1;
        }
        return 0;
    }
    public static int validateQuantityOfArgumentsForTwo(String[] args){
        int n_args = 2;
        if(validateQuantityOfArguments(args, n_args)!= 0){
            return 1;
        }
        return 0;
    }
    public static int validateTypeOfSecondArg(String[] args){
        try {
            Integer.parseInt(args[1]);
            return 0;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
    
    public static int addTask(String[] args){
        String data = APIJSON.readJson();
        if(data == null){
            return 1;
        }
        List<List> lista = APIJSON.getList(data);
        APIJSON.addElement(lista, args[1]);
        String task = APIJSON.prepareFormat(lista);
        APIJSON.writeJson(task);
        return 0;
    }
    
    public static void main(String[] args) throws Exception{
        if(emptyArguments(args)!= 0){
            System.out.println("Enter at least one argument");
            return;
        }
        
        switch (args[0].toLowerCase()) {
            case "add":
                if(validateQuantityOfArgumentsForTwo(args)!= 0){
                    System.out.println("Follow this pattern '<add>' + <\"task\">");
                    break;
                }
                addTask(args);
                break;
            default:
                System.out.println("Incorrect arguments");;
        }
    }
}
