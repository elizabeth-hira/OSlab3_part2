import java.io.IOException;
import java.io.PrintWriter;

public class Task {

    public enum Status {QUEUED, PROCESSING, DONE }

    public enum TaskType { FACTORIAL, PRIME }


    private Status status;
    private TaskType taskType;

    private final int ID;
    private static int nextID = 0;

    public int getID() {
        return ID;
    }

    public TaskType getTaskType(){return taskType;}

    public Task(TaskType taskType) {
        ID = nextID++;
        this.status = Status.QUEUED;
        this.taskType = taskType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void printResult(PrintWriter out) throws IOException {}
}