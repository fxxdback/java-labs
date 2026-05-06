package lab1;

import java.util.Collections;
import java.util.List;

public interface Item {
    String getName();
    double getPrice();
    double getWeight();

    default List<Item> getChildItemList() {
        return Collections.emptyList();
    }
}