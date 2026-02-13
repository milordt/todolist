import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Shutdown {
public Shutdown(ArrayList<Task> todoList){
    run(todoList);
}

    private void run(ArrayList<Task> todoList){
        ArrayList<String> outputLines = new ArrayList<>();

    for (Task current : todoList) {
        String line = current.getDescription() + "§" +
                      current.getPriority() + "§" +
                      current.isCompleted();
        outputLines.add(line);
    }
    Files.write(Startup.tasksPath, outputLines);
    System.out.println("Data saved successfully!");
}
}
