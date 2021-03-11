/**
Запускается отдельным процессом и отправляет запросы на сервер
с помощью класса Client.
 */

import java.math.BigInteger;

public class TestMain {

    public static void main(String[] args) throws Exception {
        int id = new Client("localhost", 8080).sendPost("POST countFactorial=5");

        if(id != 0) {
            System.out.println("Testing failed");
            System.exit(-1);
        }

        BigInteger ans = new Client("localhost", 8080).sendGet("GET id=0");

        if(!ans.equals(BigInteger.valueOf(120))) {
            System.out.println("Testing failed");
            System.exit(-2);
        }

        System.out.println("Test passed");
    }
}