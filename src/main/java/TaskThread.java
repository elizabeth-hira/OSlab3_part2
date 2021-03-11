public class TaskThread extends Thread {
    private Task task;

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
            task.setStatus(Task.Status.PROCESSING);
            task.execute();
            task.setStatus(Task.Status.DONE);
        }
    }
}