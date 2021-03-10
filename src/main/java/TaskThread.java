public class TaskThread extends Thread {

    public enum TaskType { FACTORIAL, PRIME }

    private Task task;
    private TaskType type;

    public TaskThread(Task task, TaskType type) {
        this.task = task;
        this.type = type;
    }

    @Override
    public void run() {
        task.setStatus(Task.Status.PROCESSING);
        switch(type) {
            case FACTORIAL:
            {
                ((Factorial)task).countFactorial();
                break;
            }
            case PRIME:
            {
                ((Prime)task).countPrime();
                break;
            }
        }
        task.setStatus(Task.Status.DONE);
    }
}