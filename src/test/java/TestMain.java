import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TestMain {

    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) throws Exception {
        clientSocket = new Socket("localhost", 8080);
        reader = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

//        while(true) {
//            System.out.println("Enter request: ");
//            String line = reader.readLine();
//            out.write(line + "\n");
//            out.flush();
//
//            boolean f = false;
//            while((line = in.readLine()) != null) {
//                System.out.println(line);
//                if(line.equals("stop")) {
//                    f = true;
//                    break;
//                }
//            }
//            if(f) break;
//        }

        System.out.println("Enter request: ");
        String line = reader.readLine();
        out.write(line + "\n");
        out.flush();


        while((line = in.readLine()) != null) {
            System.out.println(line);
            if (line.equals("stop"))
                break;
        }

        out.close();
        in.close();
        reader.close();
        clientSocket.close();
    }
}
