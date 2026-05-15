package lab1;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Basket basket = Basket.getInstance();
        Stack<Command> history = new Stack<>();

        System.out.println("Добро пожаловать в SmartShop!");

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Добавить Молоко (100 руб.)");
            System.out.println("2. Добавить Сыр (Акция! 200 руб.)");
            System.out.println("3. Добавить Подарочный набор (Набор)");
            System.out.println("4. ОТМЕНИТЬ последнее добавление (Undo)");
            System.out.println("5. ПЕРЕЙТИ К ОПЛАТЕ");
            System.out.print("Выберите пункт: ");

            int choice = scanner.nextInt();
            if (choice == 5) break;

            Item item = null;
            if (choice == 1) {
                item = ProductFactory.createFood("Молоко", 100, 1.0);
            } else if (choice == 2) {
                Item cheese = ProductFactory.createFood("Сыр", 200, 0.4);

                item = new ExpirationDiscountDecoratorItem(cheese, LocalDate.now());
            } else if (choice == 3) {
                BundleItem box = new BundleItem("Набор 'Завтрак'");
                box.add(ProductFactory.createFood("Кофе", 150, 0.2));
                box.add(ProductFactory.createFood("Булочка", 50, 0.1));
                item = box;
            } else if (choice == 4) {
                if (!history.isEmpty()) {
                    history.pop().undo();
                    System.out.println("Действие отменено.");
                } else {
                    System.out.println("История пуста!");
                }
                continue;
            }

            if (item != null) {
                Command cmd = new AddToBasketCommand(basket, item);
                cmd.execute();
                history.push(cmd);
                System.out.println("Добавлено: " + item.getName());
            }
        }

        System.out.println("\nВыберите вашу категорию для скидки:");
        System.out.println("1. Студент (10%)");
        System.out.println("2. Пенсионер (15%)");
        System.out.println("3. Без скидки");
        int discountChoice = scanner.nextInt();

        ReceiptPrinter printer;
        if (discountChoice == 1) {
            printer = new ReceiptPrinter() {
                @Override
                protected double getDiscountedPrice(double sum) { return new StudentDiscount().calculate(sum); }
            };
        } else if (discountChoice == 2) {
            printer = new ReceiptPrinter() {
                @Override
                protected double getDiscountedPrice(double sum) { return new PensionerDiscount().calculate(sum); }
            };
        } else {
            printer = new ReceiptPrinter() {
                @Override
                protected double getDiscountedPrice(double sum) { return sum; }
            };
        }

        printer.printReceipt(basket.getItemList());
        scanner.close();
    }
}