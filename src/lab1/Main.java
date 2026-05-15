package lab1;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Basket basket = Basket.getInstance();
    private static final Stack<Command> history = new Stack<>();

    public static void main(String[] args) {

        boolean running = true;
        while (running) {
            System.out.println("\n[ГЛАВНОЕ МЕНЮ]");
            System.out.println("1. КАТАЛОГ (Добавить товары)");
            System.out.println("2. ОТМЕНИТЬ последнее действие (Undo)");
            System.out.println("3. ПЕРЕЙТИ К ОПЛАТЕ И ВЫЙТИ");
            System.out.print("Выберите пункт: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> showCatalogMenu();
                case 2 -> undoLastAction();
                case 3 -> {
                    processPayment();
                    running = false;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showCatalogMenu() {
        boolean inCatalog = true;
        while (inCatalog) {
            System.out.println("\n--- КАТАЛОГ ТОВАРОВ ---");
            System.out.println("1. Молоко (100 руб.)");
            System.out.println("2. Сыр (Акция! 200 руб.)");
            System.out.println("3. Подарочный набор 'Завтрак' (Композит)");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Выберите товар: ");

            int choice = readInt();
            Item item = null;

            switch (choice) {
                case 1 -> item = ProductFactory.createFood("Молоко", 100, 1.0);
                case 2 -> {
                    Item cheese = ProductFactory.createFood("Сыр", 200, 0.4);
                    item = new ExpirationDiscountDecoratorItem(cheese, LocalDate.now());
                }
                case 3 -> {
                    BundleItem bundle = new BundleItem("Набор 'Завтрак'");
                    bundle.add(ProductFactory.createFood("Кофе", 150, 0.2));
                    bundle.add(ProductFactory.createFood("Круассан", 80, 0.15));
                    item = bundle;
                }
                case 0 -> inCatalog = false;
                default -> System.out.println("Неверный товар!");
            }

            if (item != null) {
                Command cmd = new AddToBasketCommand(basket, item);
                cmd.execute();
                history.push(cmd);
                System.out.println(">> Добавлено: " + item.getName());
            }
        }
    }

    private static void undoLastAction() {
        if (!history.isEmpty()) {
            Command lastCmd = history.pop();
            lastCmd.undo();
            System.out.println(">> Последнее действие отменено.");
        } else {
            System.out.println(">> История пуста!");
        }
    }

    private static void processPayment() {
        if (basket.getItemList().isEmpty()) {
            System.out.println("Корзина пуста. До свидания!");
            return;
        }

        System.out.println("\nВыберите категорию для скидки:");
        System.out.println("1. Студент (10%)");
        System.out.println("2. Пенсионер (15%)");
        System.out.println("3. Без скидки");
        int discountChoice = readInt();

        ReceiptPrinter printer = createPrinter(discountChoice);
        printer.printReceipt(basket.getItemList());
    }

    private static ReceiptPrinter createPrinter(int choice) {
        return new ReceiptPrinter() {
            @Override
            protected double getDiscountedPrice(double sum) {
                return switch (choice) {
                    case 1 -> new StudentDiscount().calculate(sum);
                    case 2 -> new PensionerDiscount().calculate(sum);
                    default -> sum;
                };
            }
        };
    }

    private static int readInt() {
        try {
            int val = Integer.parseInt(scanner.nextLine());
            return val;
        } catch (Exception e) {
            return -1;
        }
    }
}