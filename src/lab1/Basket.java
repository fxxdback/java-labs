package lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {
    private static Basket instance;
    private final List<Item> itemList = new ArrayList<>();

    private Basket() {}

    public static Basket getInstance() {
        if (instance == null) {
            instance = new Basket();
        }
        return instance;
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    public List<Item> getItemList() {
        return Collections.unmodifiableList(itemList);
    }
}