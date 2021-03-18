import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void integrationTesting() throws Exception {
        Client c1 = new Client("localhost", 8080);
        Client c2 = new Client("localhost", 8080);

        int id = c1.sendPost("POST countFactorial=5");
        BigInteger ans = c2.sendGet("GET id=0");

        c1.closeResources();
        c2.closeResources();

        assertEquals(0, id);
        assertEquals(BigInteger.valueOf(120), ans);
    }
}