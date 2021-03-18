import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Stack;

public class Client {
    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;

    public Client(String host, int port) throws Exception {
        client = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream());
    }

    public int sendPost(String line) throws Exception {
        out.println(line);
        out.flush();

        Stack<String> lines = new Stack<>();
        while((line = in.readLine()) != null) {
            lines.push(line);
        }

        return Integer.parseInt(lines.peek());
    }

    public BigInteger sendGet(String line) throws Exception {
        out.println(line);
        out.flush();

        Stack<String> lines = new Stack<>();
        while((line = in.readLine()) != null) {
            lines.push(line);
        }

        return new BigInteger(lines.peek());
    }

    public void closeResources() throws Exception {
        in.close();
        out.close();
        client.close();
    }
}