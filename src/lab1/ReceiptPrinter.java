package lab1;
import java.util.List;

public abstract class ReceiptPrinter {
    public final void printReceipt(List<Item> items) {
        System.out.println("\n=========== ЧЕК ===========");
        double total = 0;
        for (Item item : items) {
            System.out.printf("- %-20s | %.2f руб.%n", item.getName(), item.getPrice());
            total += item.getPrice();
        }
        System.out.println("-----------------------------");
        System.out.printf("СУММА: %.2f руб.%n", total);
        System.out.printf("ИТОГО СО СКИДКОЙ: %.2f руб.%n", getDiscountedPrice(total));
        System.out.println("=============================\n");
    }

    protected abstract double getDiscountedPrice(double sum);
}
