import java.io.PrintWriter;
import java.math.BigInteger;

public class Factorial extends Task{

    private BigInteger result;
    private int number;

    public Factorial(int number) {
        super(TaskType.FACTORIAL);
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

    public BigInteger countFactorial() {
        result = BigInteger.ONE;
        for (int i = 1; i <= number; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    @Override
    public void printResult(PrintWriter out) {
        out.println(getResult());
        out.flush();
    }
}