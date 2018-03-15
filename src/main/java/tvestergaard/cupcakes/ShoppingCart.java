package tvestergaard.cupcakes;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ShoppingCart implements Iterable<ShoppingCart.Item>
{
    private List<Item> items = new ArrayList<>();

    public static class Item
    {
        private final Bottom  bottom;
        private final Topping topping;
        private final int     amount;

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

        public String getFormattedUnitPrice()
        {
            int price   = getUnitPrice();
            int cents   = price % 100;
            int dollars = (price - cents) / 100;

            return dollars + "." + (cents < 9 ? "0" + cents : cents);
        }

        public int getTotalPrice()
        {
            return getUnitPrice() * amount;
        }

        public String getFormattedTotalPrice()
        {
            int price   = getTotalPrice();
            int cents   = price % 100;
            int dollars = (price - cents) / 100;

            return dollars + "." + (cents < 9 ? "0" + cents : cents);
        }
    }

    public int getTotal()
    {
        int            total        = 0;
        Iterator<Item> itemIterator = iterator();
        while (itemIterator.hasNext()) {
            total += itemIterator.next().getTotalPrice();
        }

        return total;
    }

    public String getFormattedTotal()
    {
        int price   = getTotal();
        int cents   = price % 100;
        int dollars = (price - cents) / 100;

        return dollars + "." + (cents < 9 ? "0" + cents : cents);
    }

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

    public void addItem(Item item)
    {
        items.add(item);
    }

    public int size()
    {
        return items.size();
    }
}
