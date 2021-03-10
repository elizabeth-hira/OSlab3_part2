public class TaskThread extends Thread {


    private Task task;
    private Task.TaskType type;

    public TaskThread() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                task = WebServer.tasksQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            type = task.getTaskType();

            task.setStatus(Task.Status.PROCESSING);
            switch (type) {
                case FACTORIAL: {
                    ((Factorial) task).countFactorial();
                    break;
                }
                case PRIME: {
                    ((Prime) task).countPrime();
                    break;
                }
            }
            task.setStatus(Task.Status.DONE);
        }
    }
}