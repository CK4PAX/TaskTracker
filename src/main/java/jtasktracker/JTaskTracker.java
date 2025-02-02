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
    
    public static int ValidateSecondArgOfListCase(String name){
        String[] options = {"todo","done","in-progress"};
        for (String option : options) 
            if(name.equals(option))
                return 0;
        return 1;
    }
    
    public static int validateQuantityOfArguments(String[] args, int n){
        if(args.length != n){
            return 1;
        }
        return 0;
    }
    
    public static int validateQuantityOfArgumentsForOne(String[] args){
        int n_args = 1;
        if(validateQuantityOfArguments(args, n_args)!= 0){
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
    
    public static int validateQuantityOfArgumentsForThree(String[] args){
        int n_args = 3;
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
        
        String data = APIJSON.readJson(); // 1.read json
        List<List> lista = APIJSON.getList(data);   //2. convert String into List
        int id = APIJSON.addElement(lista, args[1]); // 3. Add element to the list
        String task = APIJSON.prepareFormat(lista); // 4. convert List into String
        APIJSON.writeJson(task); // 5. weite json
        return id;
    }
    
    public static int existTask(String id){
        String data = APIJSON.readJson();
        List<List> lista = APIJSON.getList(data);
        int result = APIJSON.existTask(lista, Integer.parseInt(id));
        return result;
    }
    
    public static int deleteTask(String[] args){
        String data  = APIJSON.readJson();
        List<List> lista = APIJSON.getList(data);
        int id = Integer.parseInt(args[1]);
        APIJSON.deleteTask(lista, id);
        String tasks = APIJSON.prepareFormat(lista);
        APIJSON.writeJson(tasks);
        return 0;
    }
    
    public static int updateTask(String[] args){
        String data = APIJSON.readJson();
        List<List> lista = APIJSON.getList(data);
        int id = Integer.parseInt(args[1]);
        APIJSON.updateTask(lista, id, args[2]);
        String tasks = APIJSON.prepareFormat(lista);
        APIJSON.writeJson(tasks);
        return 0;
    }
    
    public static int getFormattedTasks(List<List> tasks){
        if(tasks.isEmpty()){
            return 0;
        }
        for (List task : tasks) {
            String format = String.format("ID:%d %s",task.get(0),task.get(1));
            System.out.println(format);
        }
        return 0;
    }
    
    public static int getList(){
        String data = APIJSON.readJson();
        List<List> tasks = APIJSON.getList(data);
        if(tasks.isEmpty()){
            return 1;
        }
        getFormattedTasks(tasks);
        return 0;
    }
    
    public static int getAdvancedList(String[] args){
        
        String data = APIJSON.readJson();
        List<List> tasks = APIJSON.getList(data);
        List<List> filtered_tasks = APIJSON.getAdvancedList(tasks, args[1]);

        if(filtered_tasks.isEmpty()){
            return 1;
        }
        
        getFormattedTasks(filtered_tasks);
        return 0;
    }
    
    public static int markInProgress(String[] args){
        String data = APIJSON.readJson();
        List<List> tasks = APIJSON.getList(data);
        APIJSON.markInProgressTask(tasks, Integer.parseInt(args[1]));
        String format = APIJSON.prepareFormat(tasks);
        APIJSON.writeJson(format);
        return 0;
    }
    
    public static int markDone(String[] args){
        String data = APIJSON.readJson();
        List<List> tasks = APIJSON.getList(data);
        APIJSON.markDoneTask(tasks, Integer.parseInt(args[1]));
        String format = APIJSON.prepareFormat(tasks);
        APIJSON.writeJson(format);
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
                int id = addTask(args);
                System.out.printf("Task added successfully. (ID: %d)%n",id);
                break;
                
            case "delete":
                if(validateQuantityOfArgumentsForTwo(args)!= 0){
                    System.out.println("Follow this pattern <delete> + <'id'>");
                    break;
                }
                if(validateTypeOfSecondArg(args)!= 0){
                    System.out.println("The second argument(id) has to be a number");
                    break;
                }
                if(existTask(args[1]) != 0){
                    System.out.println("This id does not exist");
                    break;
                }
                deleteTask(args);
                break;
                
            case "update":
                if(validateQuantityOfArgumentsForThree(args)!= 0){
                    System.out.println("Follow "
                            + "this pattern <update> + <'id'> + <\"new task\">");
                    break;
                }
                if(validateTypeOfSecondArg(args)!= 0){
                    System.out.println("The second argument(id) has to be a number");
                    break;
                }
                if(existTask(args[1]) != 0){
                    System.out.println("This id does not exist");
                    break;
                }
                updateTask(args);
                break;
                
            case "list":
                if(validateQuantityOfArgumentsForOne(args) == 0){
                    int result = getList();
                    if(result != 0){
                        System.out.println("There are no task");
                    }
                    break;
                }
                if(validateQuantityOfArgumentsForTwo(args) != 0){
                    System.out.println("Follow this pattern "
                            + "<list> + <\"todo\"|\"in-progress\"|\"done\">");
                    break;
                }
                if(ValidateSecondArgOfListCase(args[1]) != 0){
                    System.out.println("Second arg has to be one of these:"
                            + " <\"todo\"|\"in-progress\"|\"done\">");
                    break;
                }
                int result = getAdvancedList(args);
                if(result != 0){
                     System.out.println("There are no task");
                }
                break;
                
            case "mark-in-progress":
                if(validateQuantityOfArgumentsForTwo(args)!= 0){
                    System.out.println("Follow "
                            + "this pattern <mark-in-progress> + <'id'>");
                    break;
                }
                if(validateTypeOfSecondArg(args)!= 0){
                    System.out.println("The second argument(id) has to be a number");
                    break;
                }
                if(existTask(args[1]) != 0){
                    System.out.println("This id does not exist");
                    break;
                }
                markInProgress(args);
                break;
            
            case "mark-done":
                if(validateQuantityOfArgumentsForTwo(args)!= 0){
                    System.out.println("Follow "
                            + "this pattern <mark-done> + <'id'>");
                    break;
                }
                if(validateTypeOfSecondArg(args)!= 0){
                    System.out.println("The second argument(id) has to be a number");
                    break;
                }
                if(existTask(args[1]) != 0){
                    System.out.println("This id does not exist");
                    break;
                }
                markDone(args);
                break;
            default:
                System.out.println("Incorrect arguments");;
        }
    }
}
