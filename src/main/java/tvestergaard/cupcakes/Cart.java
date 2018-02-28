package tvestergaard.cupcakes;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Cart implements Iterable<Cart.Item>
{

    private static String SESSION_KEY = "cart";

    private HttpServletRequest request;
    private HttpSession        session;
    private List<Item>         items;

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

        public int getAmount()
        {
            return this.amount;
        }
    }

    public Cart(HttpServletRequest request)
    {
        this.request = request;
        this.session = request.getSession();
        this.items = getItems();
        request.setAttribute(SESSION_KEY, this.items);
    }

    private List<Item> getItems()
    {
        Object fromSession = this.session.getAttribute(SESSION_KEY);

        if (fromSession == null) {
            List<Item> items = new ArrayList<>();
            this.session.setAttribute(SESSION_KEY, items);
            return items;
        }

        return (List<Item>) fromSession;
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

    public class ItemIterator implements java.util.Iterator<Item>
    {

        int counter = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
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
