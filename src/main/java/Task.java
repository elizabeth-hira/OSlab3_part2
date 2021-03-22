public abstract class Task {

    public enum Status {PROCESSING, DONE }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public abstract void execute();

    public abstract String response();
}