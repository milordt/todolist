public class Task {
    private boolean isCompleted;
    private String description;
    private int priority;
    
    public Task(String description, int priority, boolean isCompleted){
        this.description = description;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    public String getDescription(){
        return description;
    }
    public int getPriority(){
        return priority;
    }
    public boolean isCompleted(){
        return isCompleted;
    }
    
    public void markComplete(){
        this.isCompleted = true;
    }
     public void markIncomplete(){
        this.isCompleted = false;
    }
    
    @Override
    public String toString(){
        String status = "";
        if (this.isCompleted) status = "[X]";
        return status + " " + description;
    }

}

