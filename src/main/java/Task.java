import lombok.Getter;
import lombok.Setter;

public abstract class Task {

    public enum Status {PROCESSING, DONE }

    @Getter @Setter
    private Status status;

    private final int ID;
    private static int nextID = 0;

    public int getID() {
        return ID;
    }

    public Task() {
        ID = nextID++;
        this.status = Status.PROCESSING;
    }

    public abstract void execute();

    public abstract String response();
}