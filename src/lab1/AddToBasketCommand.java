package lab1;

public class AddToBasketCommand implements Command {
    private final Basket basket;
    private final Item item;

    public AddToBasketCommand(Basket basket, Item item) {
        this.basket = basket;
        this.item = item;
    }

    @Override
    public void execute() { basket.addItem(item); }

    @Override
    public void undo() { basket.removeItem(item); }
}
