import java.net.ServerSocket;
import java.util.concurrent.*;


public class WebServer implements Runnable {
    public static ConcurrentHashMap<Integer, Task> tasksMap = new ConcurrentHashMap<>();
    private final ExecutorService pool;

    private final ServerSocket server;

    public WebServer(int port, int poolSize) throws Exception {
        server = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        try {
            while (true) {
                pool.execute(new TaskThread(server.accept()));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }
}