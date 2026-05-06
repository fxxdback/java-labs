package lab1;

public class StudentDiscount implements Discount {
    private static final double STUDENT_DISCOUNT_RATE = 0.9;

    @Override
    public double calculate(double sum) {
        return sum * STUDENT_DISCOUNT_RATE;
    }
}