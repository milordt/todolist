import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Startup{
    public static final Path tasksPath = Paths.get("tasks.txt");

    public Startup(){
    run();
    }

    private void run(){
    try {
        if (Files.exists(tasksPath)){
            returning();
            } else {
                newUser();
    }
    } catch (IOException e) {
        System.err.println("An error occurred, " + e.getMessage());
    }
}

    private void returning(){
        System.out.println("Welcome back user. Loading in your tasks now.");
        TodoList t = new TodoList(loadedTasks());


    }
    private void newUser() throws IOException {
        System.out.println("Welcome! Creating a new task file for you!");
        Files.createFile(tasksPath);
        System.out.println("File successfully created!");
        TodoList t = new TodoList();
    }

   private ArrayList<Task> loadedTasks() {
    ArrayList<Task> tasks = new ArrayList<>();

    try {
        List<String> lines = Files.readAllLines(tasksPath);

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split("§");

            if (parts.length == 3) {
                String desc = parts[0];
                int prio = Integer.parseInt(parts[1].trim());
                boolean complete = Boolean.parseBoolean(parts[2].trim());
                tasks.add(new Task(desc, prio, complete));
            }
        }
    } catch (IOException e) {
        System.err.println("Failed to load tasks: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Error: One of the priority numbers in the file is corrupted.");
    }
    return tasks;
        }
    }

