package tvestergaard.cupcakes;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ShoppingCart implements Iterable<ShoppingCart.Item>
{

    /**
     * The items contained in the shopping cart.
     */
    private List<Item> items = new ArrayList<>();

    /**
     * Represents an item in the shopping cart.
     */
    public static class Item
    {

        /**
         * The bottom of the item.
         */
        private final Bottom bottom;

        /**
         * The topping of the item.
         */
        private final Topping topping;

        /**
         * The amount of cupcakes to order.
         */
        private final int amount;

        /**
         * Creates a new item.
         *
         * @param bottom  The bottom of the item.
         * @param topping The topping of the item.
         * @param amount  The amount of cupcakes to order.
         */
        public Item(Bottom bottom, Topping topping, int amount)
        {
            this.bottom = bottom;
            this.topping = topping;
            this.amount = amount;
        }

        public Bottom getBottom()
        {
            return this.bottom;
        }

        public Topping getTopping()
        {
            return this.topping;
        }

        public int getQuantity()
        {
            return this.amount;
        }

        public int getUnitPrice()
        {
            return bottom.getPrice() + topping.getPrice();
        }

        public int getTotalPrice()
        {
            return getUnitPrice() * amount;
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
        return new ItemIterator();
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public class ItemIterator implements java.util.Iterator<Item>
    {

        int counter = 0;

        /**
         * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
         * #next} would return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override public boolean hasNext()
        {
            return counter < size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws java.util.NoSuchElementException if the iteration has no more elements
         */
        @Override public Item next()
        {
            if (counter >= size())
                throw new NoSuchElementException();

            return items.get(counter++);
        }
    }

    /**
     * Adds the provided item to the cart.
     *
     * @param item The item to add to the cart.
     */
    public void addItem(Item item)
    {
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
}
