public class Controllers {
    private void executeTaskAsync(Task task) {
        WebServer.tasksMap.put(task.getID(), task);
        WebServer.poolTasks.execute(() -> {
            task.setStatus(Task.Status.PROCESSING);
            task.execute();
            task.setStatus(Task.Status.DONE);
        });
    }

    @ControllerType(type=Request.RequestType.POST, name="countFactorial")
    public String countFactorial(Request request) {
        Task task = new Factorial(Integer.parseInt(request.getValue()));
        executeTaskAsync(task);
        return String.valueOf(task.getID());
    }
    @ControllerType(type=Request.RequestType.POST, name="countPrime")
    public String countPrime(Request request) {
        Task task = new Prime(Integer.parseInt(request.getValue()));
        executeTaskAsync(task);
        return String.valueOf(task.getID());
    }

    @ControllerType(type=Request.RequestType.GET, name="id")
    public String getInfo(Request request) {
        Task task = WebServer.tasksMap.get(Integer.parseInt(request.getValue()));
        if (task == null) return "Error: No such id found.";
        if (task.getStatus() != Task.Status.DONE) return "Status: " + task.getStatus().name();
        return task.response();
    }
}