public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Server started.");
        new Thread(new WebServer(8080, 4)).start();
    }
}