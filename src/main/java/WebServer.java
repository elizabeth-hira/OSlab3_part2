import java.net.ServerSocket;
import java.util.concurrent.*;


public class WebServer implements Runnable {
    public static ConcurrentHashMap<Integer, Task> tasksMap = new ConcurrentHashMap<>();
    public static ExecutorService poolTasks;
    private final ExecutorService poolRequests;

    private final ServerSocket server;

    public WebServer(int port, int poolSize) throws Exception {
        server = new ServerSocket(port);
        poolTasks = Executors.newFixedThreadPool(poolSize);
        poolRequests = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        try {
            while (!server.isClosed()) {
                poolRequests.execute(new TaskThread(server.accept()));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            poolRequests.shutdown();
            poolTasks.shutdown();
        }
    }
}