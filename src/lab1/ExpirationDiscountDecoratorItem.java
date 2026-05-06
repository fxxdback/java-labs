package lab1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExpirationDiscountDecoratorItem implements Item {
    private static final int EXPIRATION_THRESHOLD_DAYS = 2;
    private static final double EXPIRATION_DISCOUNT_RATE = 0.9;

    private final Item item;
    private final LocalDate expirationDate;

    public ExpirationDiscountDecoratorItem(Item item, LocalDate expirationDate) {
        this.item = item;
        this.expirationDate = expirationDate;
    }

    @Override
    public String getName() {
        if (isExpiringSoon()) {
            return item.getName() + " (АКЦИЯ: Срок истекает!)";
        }
        return item.getName();
    }

    @Override
    public double getPrice() {
        if (isExpiringSoon()) {
            return item.getPrice() * EXPIRATION_DISCOUNT_RATE;
        }
        return item.getPrice();
    }

    @Override
    public double getWeight() {
        return item.getWeight();
    }

    private boolean isExpiringSoon() {
        long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
        return daysUntilExpiration <= EXPIRATION_THRESHOLD_DAYS;
    }
}