import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class Prime extends Task{

    private BigInteger number;
    private int bytes;

    public Prime(Task.TaskType taskType, int bytes) {
        super(taskType);
        this.bytes = bytes;
    }

    public BigInteger getResult() {
        return number;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public BigInteger countPrime() {
        number = testFerma(bytes);
        return getResult();
    }
    public BigInteger findNumber(int bytes) {
        Random random = new Random();

        BigInteger number;
        number = new BigInteger(bytes, random).setBit(0);

        return number;
    }

    public BigInteger testFerma(int bytes) {
        Random random = new Random();
        while(true) {
            BigInteger number = findNumber(bytes);
            BigInteger a;
            for (int i = 0; i < 1000; i++) {
                do {
                    a = new BigInteger(bytes, random);
                } while (a.compareTo(number) >= 0);

                if (a.gcd(number).equals(BigInteger.ONE))
                    continue;

                BigInteger rest = a.modPow(number.subtract(BigInteger.ONE), number);

                if (!rest.equals(BigInteger.ONE))
                    continue;
            }
            return number;
        }
    }

    @Override
    public void printResult(PrintWriter out) {
        out.println(getResult());
        out.flush();
    }
}