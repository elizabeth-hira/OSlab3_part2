import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


public class WebServer {
    private static final int PORT = 8080;
    public static ConcurrentHashMap<Integer, Task> tasksMap;
    public static LinkedBlockingQueue<Socket> tasksQueue;
    private static int threadNum = 4;
    ExecutorService pool = Executors.newCachedThreadPool();

    private static class ServerThread extends Thread {
        @Override
        public void run() {
            try (ServerSocket server = new ServerSocket(PORT)) {
                try {
                    for (int i = 0; i < WebServer.threadNum; i++) {
                        new TaskThread().start();
                    }
                    while (true) {
                        Socket client = server.accept();
                        tasksQueue.put(client);
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            catch(Exception err) {
                err.printStackTrace();
            }
        }
    }

    public WebServer() {
        tasksMap = new ConcurrentHashMap<>();
        tasksQueue = new LinkedBlockingQueue<>();
    }

    public void start() {
        ServerThread thread = new ServerThread();
        pool.execute(thread);
    }
}