import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void integrationTesting() throws Exception {
        int id = new Client("localhost", 8080).sendPost("POST countFactorial=5");
        BigInteger ans = new Client("localhost", 8080).sendGet("GET id=0");

        assertEquals(0, id);
        assertEquals(BigInteger.valueOf(120), ans);
    }
}