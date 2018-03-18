package tvestergaard.cupcakes;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart implements Iterable<ShoppingCart.Item>
{

    /**
     * The items contained in the shopping cart.
     */
    private List<Item> items = new ArrayList<>();

    /**
     * Represents an item in the shopping cart. An item consists of a product (bottom and topping) and the number of
     * that product to be ordered.
     */
    public static class Item
    {

        /**
         * The bottom of the product to order.
         */
        private final Bottom bottom;

        /**
         * The topping of the product to order.
         */
        private final Topping topping;

        /**
         * The quantity of cupcakes with the bottom and topping to order.
         */
        private final int quantity;

        /**
         * Creates a new item.
         *
         * @param bottom   The bottom of the item.
         * @param topping  The topping of the item.
         * @param quantity The quantity of cupcakes to order.
         */
        public Item(Bottom bottom, Topping topping, int quantity)
        {
            this.bottom = bottom;
            this.topping = topping;
            this.quantity = quantity;
        }

        /**
         * Returns the bottom of the product to order.
         *
         * @return The bottom of the product to order.
         */
        public Bottom getBottom()
        {
            return this.bottom;
        }

        /**
         * Returns the topping of the product to order.
         *
         * @return The topping of the product to order.
         */
        public Topping getTopping()
        {
            return this.topping;
        }

        /**
         * Returns the quantity of cupcakes with the bottom and topping to order.
         *
         * @return The quantity of cupcakes with the bottom and topping to order.
         */
        public int getQuantity()
        {
            return this.quantity;
        }

        /**
         * Returns the price of a single cupcake with the provided bottom and topping.
         *
         * @return the price of a single cupcake with the provided bottom and topping.
         */
        public int getUnitPrice()
        {
            return bottom.getPrice() + topping.getPrice();
        }

        /**
         * Returns the price of the quantity of cupcakes with the provided bottom and topping.
         *
         * @return The price of the quantity of cupcakes with the provided bottom and topping.
         */
        public int getTotalPrice()
        {
            return getUnitPrice() * quantity;
        }
    }

    /**
     * Returns the total price of the cart.
     *
     * @return The total price of the cart.
     */
    public int getTotal()
    {
        int            total        = 0;
        Iterator<Item> itemIterator = iterator();
        while (itemIterator.hasNext()) {
            total += itemIterator.next().getTotalPrice();
        }

        return total;
    }

    /**
     * Empties the cart, removing all items.
     */
    public void clear()
    {
        this.items.clear();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override public Iterator<Item> iterator()
    {
        return items.iterator();
    }

    /**
     * Checks that the shopping cart is empty.
     *
     * @return {@code true} if the shopping cart is empty, otherwise {@code false}.
     */
    public boolean isEmpty()
    {
        return size() == 0;
    }

    /**
     * Adds the provided item to the cart. When an item with the same bottom and topping already exists in the
     * {@link ShoppingCart}, the quantity of that item is instead increased.
     *
     * @param item The item to add to the cart.
     */
    public void addItem(Item item)
    {
        Bottom  bottom  = item.getBottom();
        Topping topping = item.getTopping();

        for (int x = 0; x < items.size(); x++) {
            Item forItem = items.get(x);
            if (forItem.getBottom().equals(bottom) && forItem.getTopping().equals(topping)) {
                items.set(x, new Item(bottom, topping, forItem.quantity + item.quantity));
                return;
            }
        }

        items.add(item);
    }

    /**
     * Returns the number of items in the cart.
     *
     * @return The number of items in the cart.
     */
    public int size()
    {
        return items.size();
    }

    /**
     * Returns a list of the items in the shopping cart.
     *
     * @return The list of the items in the shopping cart.
     */
    public List<Item> getItems()
    {
        return new ArrayList<>(items);
    }
}
