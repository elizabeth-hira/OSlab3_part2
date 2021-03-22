import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Controllers {
    private static void executeTaskAsync(Task task) {
        Future<Task> future = WebServer.poolTasks.submit(() -> { // Асинхронное выполнение
            task.setStatus(Task.Status.PROCESSING);
            task.execute();
            task.setStatus(Task.Status.DONE);
            return task;
        });
        WebServer.tasksMap.put(task.getID(), future);
    }

    @ControllerType(type=Request.RequestType.POST, name="countFactorial")
    public static String countFactorial(Request request) {
        Task task = new Factorial(Integer.parseInt(request.getValue()));
        executeTaskAsync(task);
        return String.valueOf(task.getID());
    }
    @ControllerType(type=Request.RequestType.POST, name="countPrime")
    public static String countPrime(Request request) {
        Task task = new Prime(Integer.parseInt(request.getValue()));
        executeTaskAsync(task);
        return String.valueOf(task.getID());
    }

    @ControllerType(type=Request.RequestType.GET, name="id")
    public static String getInfo(Request request) throws ExecutionException, InterruptedException {
        Future<Task> futureTask = WebServer.tasksMap.get(Integer.parseInt(request.getValue()));
        if (futureTask == null) return "Error: No such id found.";
        if (!futureTask.isDone()) return "Status: " + Task.Status.PROCESSING.toString();
        return futureTask.get().response();
    }
}