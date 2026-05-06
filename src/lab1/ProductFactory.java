package lab1;

public class ProductFactory {
    public static Item createFood(String name, double price, double weight) {
        return new ProductItem(name, price, weight);
    }
}