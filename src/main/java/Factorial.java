import java.io.PrintWriter;
import java.math.BigInteger;

public class Factorial extends Task{

    private BigInteger result;
    private int number;

    public Factorial(int number) {
        this.number = number;
    }

    public BigInteger getResult() {
        return result;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void execute() {
        result = BigInteger.ONE;
        for (int i = 1; i <= number; i++)
            result = result.multiply(BigInteger.valueOf(i));
    }

    @Override
    public void printResult(PrintWriter out) {
        out.println(getResult());
        out.flush();
    }
}