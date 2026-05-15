package lab1;

public class PensionerDiscount implements Discount {
    @Override
    public double calculate(double sum) { return sum * 0.9; }
}
