package lab1;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Basket basket = Basket.getInstance();

        Item freshMilk = ProductFactory.createFood("Молоко", 100, 1.0);
        LocalDate expirationDate = LocalDate.now().plusDays(1);

        Item discountMilk = new ExpirationDiscountDecoratorItem(freshMilk, expirationDate);
        basket.addItem(discountMilk);

        BundleItem giftBox = new BundleItem("Подарочный набор 'Чаепитие'");

        giftBox.add(ProductFactory.createFood("Печенье овсяное", 60, 0.4));
        giftBox.add(ProductFactory.createFood("Чай черный", 120, 0.1));

        basket.addItem(giftBox);

        double totalSum = 0;
        System.out.println("СПИСОК ПОКУПОК В КОРЗИНЕ:");

        for (Item item : basket.getItemList()) {
            System.out.println(item.getName() + " - " + item.getPrice() + " руб.");

            List<Item> innerItems = item.getChildItemList();
            if (!innerItems.isEmpty()) {
                for (Item inner : innerItems) {
                    System.out.println("   [Состав набора]: " + inner.getName() + " (" + inner.getPrice() + " руб.)");
                }
            }
            totalSum += item.getPrice();
        }

        Discount studentDiscount = new StudentDiscount();
        double finalSum = studentDiscount.calculate(totalSum);

        System.out.println("К оплате без учета скидки: " + totalSum + " руб.");
        System.out.println("К оплате с учетом скидки: " + finalSum + " руб.");
    }
}