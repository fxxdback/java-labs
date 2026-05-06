package lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BundleItem implements Item {
    private final String name;
    private final List<Item> itemList = new ArrayList<>();

    public BundleItem(String name) {
        this.name = name;
    }

    public void add(Item item) {
        itemList.add(item);
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getPrice() {
        double totalPrice = 0;
        for (Item item : itemList) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    @Override
    public double getWeight() {
        double totalWeight = 0;
        for (Item item : itemList) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    @Override
    public List<Item> getChildItemList() {
        return Collections.unmodifiableList(itemList);
    }
}