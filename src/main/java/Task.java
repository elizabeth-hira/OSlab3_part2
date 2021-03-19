public abstract class Task {

    public enum Status {QUEUED, PROCESSING, DONE }

    private Status status;

    private final int ID;
    private static int nextID = 0;

    public int getID() {
        return ID;
    }

    public Task() {
        ID = nextID++;
        this.status = Status.QUEUED;
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