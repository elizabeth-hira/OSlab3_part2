import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Random;

public class Prime extends Task{

    @Getter
    private BigInteger result;

    @Getter @Setter
    private int bytes;

    public Prime(int bytes) {
        this.bytes = bytes;
    }

    @Override
    public void execute() {
        result = testFerma(bytes);
    }

    public BigInteger findNumber(int bytes) {
        BigInteger number = new BigInteger(bytes, new Random());
        return number.setBit(number.bitCount() - 1);
    }

    public BigInteger testFerma(int bytes) {
        Random random = new Random();
        while(true) {
            BigInteger number = findNumber(bytes);
            BigInteger a;

            boolean condition = false;
            for (int i = 0; i < 10 && !condition; i++) {
                a = new BigInteger(bytes, random).mod(number.subtract(BigInteger.TWO));

                if (!a.gcd(number).equals(BigInteger.ONE))
                    condition = true;

                if (!a.modPow(number.subtract(BigInteger.ONE), number).equals(BigInteger.ONE))
                    condition = true;
            }
            if(condition) continue;

            return number;
        }
    }

    @Override
    public String response() {
        return String.valueOf(getResult());
    }
}