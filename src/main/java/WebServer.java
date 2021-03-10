import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WebServer {

    private static final int PORT = 8080;

    private static ConcurrentHashMap<Integer, Task> tasksMap;
    public static LinkedBlockingQueue<Task> tasksQueue = new LinkedBlockingQueue<>();
    private static int threadNum = 4;

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

                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter out = new PrintWriter(client.getOutputStream());



                        String line = in.readLine();
                        if(line == null) {
                            continue;
                        }
                        // Start sending our reply, using the HTTP 1.1 protocol
                        out.print("HTTP/1.1 200 \r\n"); // Version & status code
                        out.print("Content-Type: text/plain\r\n"); // The type of data
                        out.print("Connection: close\r\n"); // Will close stream
                        out.print("\r\n"); // End of headers

                        Request request = Request.parse(line);
                        switch(request.type) {
                            case GET:
                            {
                                switch(request.getKey()) {
                                    case "id":
                                    {
                                        Task task = WebServer.tasksMap.get(Integer.parseInt(request.getValue()));
                                        if(task != null) {
                                            if (task.getStatus() != Task.Status.DONE) {
                                                out.println("Status: " + task.getStatus().name());
                                            }
                                            else {
                                                task.printResult(out);
                                            }
                                        }
                                        else {
                                            out.println("Error: No such id found");
                                            out.flush();
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                            case POST:
                            {
                                switch(request.getKey()) {
                                    case "countFactorial":
                                    {
                                        Factorial factorialTask = new Factorial(Task.TaskType.FACTORIAL, Integer.parseInt(request.getValue()));
                                        tasksMap.put(factorialTask.getID(), factorialTask);
                                        tasksQueue.add(factorialTask);
                                        out.println(factorialTask.getID());
                                        out.flush();
                                        break;
                                    }
                                    case "countPrime":
                                    {
                                        Prime primeTask = new Prime(Task.TaskType.PRIME, Integer.parseInt(request.getValue()));
                                        tasksMap.put(primeTask.getID(), primeTask);
                                        tasksQueue.add(primeTask);
                                        out.println(primeTask.getID());
                                        out.flush();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        in.close();
                        out.close();
                        client.close();
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
    }

    public void start() {
        new ServerThread().start();
    }
}