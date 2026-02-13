import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {
    ArrayList<Task> todoList = new ArrayList<>();
    Scanner s1 = new Scanner(System.in);
    HashMap<String, Runnable> userInput;

    public TodoList() {
        userInput = hInit(s1, todoList);
        run();
    }
    public TodoList(ArrayList<Task> todoList){
        this.todoList = todoList;
        userInput = hInit(s1, todoList);
        run();
        Shutdown s = new Shutdown(todoList);
    }

    private void run() {
        boolean a = true;
        while (a) {
            clearScreen();
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Create a new task");
            System.out.println("2. View tasks. (Mark complete or incomplete)");
            System.out.println("3. Manage tasks - edit individual tasks, clear tasks");
            System.out.println("4. Exit.\n");

            String input = "main:" + s1.nextLine().toLowerCase().trim();

            if (input.equals("main:exit") || input.equals("main:4")) {
                a = false;
                System.out.println("Goodbye!");
            } else if (userInput.containsKey(input)) {
                userInput.get(input).run();
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void manageTaskMenu(Scanner s1) {
        boolean a = true;
        while (a) {
            System.out.println("Manage menu.");
            System.out.println("1. Edit tasks.");
            System.out.println("2. Clear tasks. (Nuclear)");// ensure you double check with the user before doing this!
            System.out.println("3. Delete individual tasks.");
            System.out.println("4. Exit");

            String input = "manage:" + s1.nextLine().toLowerCase().trim();

            if (input.equals("manage:exit") || input.equals("manage:4")) {
                a = false;
                System.out.println("Goodbye!");
            } else if (userInput.containsKey(input)) {
                userInput.get(input).run();
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private HashMap<String, Runnable> hInit(Scanner s1, ArrayList<Task> todoList) {
        HashMap<String, Runnable> hash = new HashMap<String, Runnable>();

        // create task
        hash.put("main:1", () -> {
            todoList.add(createTask(s1));
            System.out.println("Task successfully added.");
        });
        hash.put("main:new task", () -> {
            todoList.add(createTask(s1));
            System.out.println("Task successfully added.");
        });
        hash.put("main:create a new task", () -> {
            todoList.add(createTask(s1));
            System.out.println("Task successfully added.");
        });

        // view tasks
        hash.put("main:2", () -> {
            if(todoList.isEmpty()){
                System.out.println("The todo list contains no tasks.");
            } else {
            viewTasks(todoList, s1);
            }
        });
        hash.put("main:view tasks", () -> {
            if(todoList.isEmpty()){
                System.out.println("The todo list contains no tasks.");
            } else {
            viewTasks(todoList, s1);
            }
        });
        hash.put("main:view", () -> {
            if(todoList.isEmpty()){
                System.out.println("The todo list contains no tasks.");
            } else {
            viewTasks(todoList, s1);
            }
        });
        hash.put("main:view all my tasks", () -> {
            if(todoList.isEmpty()){
                System.out.println("The todo list contains no tasks.");
            } else {
            viewTasks(todoList, s1);
            }
        });

        // opening manage tasks
        hash.put("main:edit", () -> {
            if(todoList.isEmpty()){
                System.out.println("You cannot manage tasks that do not exist.");
            } else {
            manageTaskMenu(s1);
            }
        });
        hash.put("main:3", () -> {
             if(todoList.isEmpty()){
                System.out.println("You cannot manage tasks that do not exist.");
            } else {
            manageTaskMenu(s1);
            }
        });
        hash.put("main:manage", () -> {
             if(todoList.isEmpty()){
                System.out.println("You cannot manage tasks that do not exist.");
            } else {
            manageTaskMenu(s1);
            }
        });
        hash.put("main:tasks", () -> {
             if(todoList.isEmpty()){
                System.out.println("You cannot manage tasks that do not exist.");
            } else {
            manageTaskMenu(s1);
            }
        });

        // exit
        hash.put("main:4", () -> {
        });
        hash.put("main:exit", () -> {
        });

        // manage tasks menu -------

        // edit tasks
        hash.put("manage:edit", () -> {
            editTask(s1, todoList);
        });
        hash.put("manage:1", () -> {
            editTask(s1, todoList);
        });

        // clear tasks
        hash.put("manage:2", () -> {
            boolean a=true;
            while(a)
            System.out.println("Are you sure you want to proceed? (There's no going back!)"+
            "n Enter y/n");
            String input = s1.nextLine().toLowerCase().trim();
            if(input.equals("y")){
                deleteTask(true, todoList);
                a=false;
            } else if (input.equals("n")){
                a=false;
            } else {
                System.out.println("Invalid input, try again");
            }

        });
        hash.put("manage:clear", () -> {
            while(true){
            System.out.println("Are you sure you want to proceed? (There's no going back!)"+
            "n Enter y/n");
            String input = s1.nextLine().toLowerCase().trim();
            if(input.equals("y")){
                deleteTask(true, todoList);
                break;
            } else if (input.equals("n")){
                break;
            } else {
                System.out.println("Invalid input, try again");
            }
        }
        });

        // delete individual tasks
        hash.put("manage:delete", () -> {
            deleteTask(false, todoList);
        });
        hash.put("manage:3", () -> {
            deleteTask(false, todoList);
        });

        // exit manage menu
        hash.put("manage:4", () -> {
        });
        hash.put("manage:exit", () -> {
        });

        return hash;
    }

    private Task createTask(Scanner s1) {
        System.out.println("What is the description of the task?");
        String description = s1.nextLine();

        int priority = -1;
        while (priority < 1 || priority > 3) {
            System.out.println("What is the priority (1-3)? Or, put in 'top' for highest prio.");
            String input = s1.nextLine().toLowerCase().trim();
            if(input.equals("top")){
                priority=3;
                break;
            } else if(input.equals("")){
                priority=1;
                break;
            }
            priority = parseIntOrNull(input);

            if (priority < 1 || priority > 3) {
                System.out.println("Please enter a number between 1 and 3.");
            }
        }

        return new Task(description, priority, false);
    }

    private void editTask(Scanner s1, ArrayList<Task> todoList) {
        justViewTasks(todoList);
        System.out.println(
                "Here is the list of tasks--select a task, either by name or number. It is zero indexed, and I don't feel like changing it.");
        String input = s1.nextLine().toLowerCase().trim();
        int task = parseIntOrNull(input);

        if (task == -1) { // didnt put in an index
            for (int i = 0; i < todoList.size(); i++) {
                if (input.equalsIgnoreCase(todoList.get(i).getDescription())) {
                    todoList.set(i, createTask(s1));
                }
            }
        } else { // put in an index
            todoList.set(task, createTask(s1));
        }
        System.out.println("Edit complete.");
        justViewTasks(todoList);
    }

    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private void renderTasks(int selectedIndex, ArrayList<Task> todolist){
        clearScreen();
        System.out.println("View task manager.");
        System.out.println(
                        "Key: \n" + //
                        "J to descend. \n" + //
                        "K to ascend. \n" + //
                        "T to mark complete. \n" + //
                        "Q to return to the menu. \n" + //
                        "All case sensitive, all ree certified.");
        System.out.println("---------------");
        for (int i = 0; i < todolist.size(); i++) {
            if (selectedIndex == i) System.out.println(" > "+i + ": " + todolist.get(i));
            else System.out.println(i + ": " + todolist.get(i));
    }
        System.out.println("---------------");
        System.out.println("End of task list.");
    }
    private void justViewTasks(ArrayList<Task> todolist){
        for (int i=0; i<todolist.size();i++){
            System.out.println(i + ": " + todolist.get(i));
        }
    }
    private void viewTasks(ArrayList<Task> todolist, Scanner s1) {
        int selectedIndex = 0;

        while (true){
            if (selectedIndex < 0) selectedIndex = 0;
            if (selectedIndex >= todolist.size()) selectedIndex = todolist.size() - 1;

            renderTasks(selectedIndex, todolist);
            String input = s1.nextLine().toLowerCase().trim();

            switch (input) {
                case "j":
                    selectedIndex++;
                    break;
                case "k":
                    selectedIndex--;
                    break;
                case "t":
                    Task selected = todolist.get(selectedIndex);
                    if (selected.isCompleted()) {
                        selected.markIncomplete();
                    } else {
                        todolist.get(selectedIndex).markComplete();
                    } break;
                case "q":
                    return;
                default:
            }
        }
        
    } 

    private Integer parseIntOrNull(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void deleteTask(boolean clear, ArrayList<Task> todolist) {
        if (clear) {
            todolist.clear();
        } else {
            while (true) {
                justViewTasks(todolist);
                System.out.println(
                        "Here is the list of tasks--select a task, either by name or number. It is zero indexed, and I don't feel like changing it.");
                String input = s1.nextLine().toLowerCase().trim();
                int task = parseIntOrNull(input);

                if (task == -1) { // didnt put in an index
                        todolist.removeIf(Task -> input.equals(Task.getDescription().toLowerCase().trim()));
                        System.out.println("Task removed.");
                        justViewTasks(todolist);
                        break;
                } else { // put in an index
                    try {
                        todolist.remove(task);
                        System.out.println("Task removed.");
                        justViewTasks(todolist);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid index.");
                        break;
                    }
                }
                
                
            }
        }
    }
}
