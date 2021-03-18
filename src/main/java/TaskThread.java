import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TaskThread extends Thread {
    private final Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public TaskThread(Socket client) {
        this.client = client;
    }

    private void closeResources() throws Exception {
        in.close();
        out.close();
        client.close();
    }

    @Override
    public void run() {
        try {
            Task task;

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

            out.print("HTTP/1.1 200 \r\n");
            out.print("\r\n");

            String line = in.readLine();
            if (line == null) {
                closeResources();
                return;
            }

            Request request = Request.parse(line);

            switch (request.type) {
                case GET: {
                    switch (request.getKey()) {
                        case "id": {
                            task = WebServer.tasksMap.get(Integer.parseInt(request.getValue()));
                            if (task != null) {
                                if (task.getStatus() != Task.Status.DONE) {
                                    out.println("Status: " + task.getStatus().name());
                                    out.flush();
                                } else {
                                    task.printResult(out);
                                }
                            } else {
                                out.println("Error: No such id found");
                                out.flush();
                            }
                            break;
                        }
                    }
                    break;
                }
                case POST: {
                    task = null;
                    switch (request.getKey()) {
                        case "countFactorial": {
                            task = new Factorial(Integer.parseInt(request.getValue()));
                            break;
                        }
                        case "countPrime": {
                            task = new Prime(Integer.parseInt(request.getValue()));
                            break;
                        }
                    }
                    if (task != null) {
                        WebServer.tasksMap.put(task.getID(), task);

                        out.println(task.getID());
                        out.flush();

                        //closeResources();

                        task.setStatus(Task.Status.PROCESSING);
                        task.execute();
                        task.setStatus(Task.Status.DONE);
                    }
                    break;
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        finally {
            try {
                closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}