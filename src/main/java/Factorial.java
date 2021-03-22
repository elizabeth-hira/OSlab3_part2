import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

public class Factorial extends Task{

    @Getter
    private BigInteger result;

    @Getter @Setter
    private int number;

    public Factorial(int number) {
        this.number = number;
    }

    @Override
    public void execute() {
        result = BigInteger.ONE;
        for (int i = 1; i <= number; i++)
            result = result.multiply(BigInteger.valueOf(i));
    }

    @Override
    public String response() {
        return String.valueOf(getResult());
    }
}