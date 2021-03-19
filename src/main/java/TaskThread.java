import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;

public class TaskThread implements Runnable {
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
            String response = null;

            Controllers controller = new Controllers();
            Class<?> clazz = controller.getClass();
            for(Method method : clazz.getDeclaredMethods()) {
                if(method.isAnnotationPresent(ControllerType.class)) {
                    ControllerType annotation = method.getAnnotation(ControllerType.class);
                    if(annotation.type().equals(request.type)
                       && annotation.name().equals(request.getKey())) {
                        response = (String) method.invoke(controller, request);
                    }
                }
            }

            out.println(response);
            out.flush();
        }
        catch (Exception error) { error.printStackTrace(); }
        finally {
            try { closeResources(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}