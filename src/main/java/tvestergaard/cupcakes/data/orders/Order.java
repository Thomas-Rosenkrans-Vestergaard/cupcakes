package tvestergaard.cupcakes.data.orders;

import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.data.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order placed by a user. Contains one or more {@link Item}s (products).
 */
public class Order
{

    /**
     * The id of the order.
     */
    private final int id;

    /**
     * The user who created the order.
     */
    private final User user;

    /**
     * The total cost of the order.
     */
    private final int total;

    /**
     * A comment to the order.
     */
    private final String comment;

    /**
     * The status of the order.
     */
    private final Status status;

    /**
     * The items ordered.
     */
    private final List<Item> items;

    /**
     * Creates a new order.
     *
     * @param id      The id of the order.
     * @param user    The user who created the order.
     * @param total   The total price of the order.
     * @param comment Optional comment given by the user.
     * @param status  The status of the order.
     * @param items   The items ordered.
     */
    public Order(int id, User user, int total, String comment, Status status, List<Item> items)
    {
        this.id = id;
        this.user = user;
        this.total = total;
        this.comment = comment;
        this.status = status;
        this.items = items;
    }

    /**
     * Returns the id of the order.
     *
     * @return The id of the order.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the user who placed the order.
     *
     * @return The user who placed the order.
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * Returns the total price of the order.
     *
     * @return The total price fo the order.
     */
    public int getTotal()
    {
        return this.total;
    }

    /**
     * Returns an optional comment provided by the user who placed the order.
     *
     * @return The optional comment provided by the user who placed the order.
     */
    public String getComment()
    {
        return this.comment;
    }

    /**
     * Returns the status of the order.
     *
     * @return The status of the order.
     */
    public Status getStatus()
    {
        return this.status;
    }

    /**
     * Returns a list of the ordered items. The returned list can be safely modified.
     *
     * @return The list of the orders items.
     */
    public List<Item> getItems()
    {
        return new ArrayList<>(items);
    }

    public enum Status
    {

        /**
         * The order has been placed, but not further processed.
         */
        PLACED(0),

        /**
         * The order is ready for pickup.
         */
        READY(1),

        /**
         * The order has been picked up.
         */
        INACTIVE(2);

        private final int code;

        Status(int code)
        {
            this.code = code;
        }

        /**
         * Returns the code representing the status.
         *
         * @return The code representing the status.
         */
        public int getCode()
        {
            return this.code;
        }

        /**
         * Static cache of the statuses to be returned from {@link Status#fromCode(int)}.
         */
        private static Status[] statuses;

        static {
            Status[] array = Status.values();
            statuses = new Status[array.length];
            for (Status status : array)
                statuses[status.getCode()] = status;
        }

        /**
         * Returns a status with the provided code.
         *
         * @param code The code of the status to return.
         * @return The status with the provided code.
         */
        public static Status fromCode(int code)
        {
            return statuses[code];
        }
    }

    /**
     * Represents an order item.
     */
    public final static class Item
    {

        /**
         * The id of the order item.
         */
        private final int id;

        /**
         * The bottom chosen for the order item.
         */
        private final Bottom bottom;

        /**
         * The topping chosen for the order item.
         */
        private final Topping topping;

        /**
         * The quantity of products ordered.
         */
        private final int quantity;

        /**
         * The unit price of the chosen cupcake.
         */
        private final int unitPrice;

        /**
         * Creates a new order item.
         *
         * @param id        The id of the order item.
         * @param bottom    The bottom chosen.
         * @param topping   The topping chosen.
         * @param quantity  The quantity of cupcakes to order.
         * @param unitPrice The unit price of the ordered cupcake.
         */
        public Item(int id, Bottom bottom, Topping topping, int quantity, int unitPrice)
        {
            this.id = id;
            this.bottom = bottom;
            this.topping = topping;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        /**
         * Returns the id of the order item.
         *
         * @return The id of the order item.
         */
        public int getId()
        {
            return this.id;
        }

        /**
         * Returns the bottom of the order item.
         *
         * @return The bottom of the order item.
         */
        public Bottom getBottom()
        {
            return this.bottom;
        }

        /**
         * Returns the topping of the order item.
         *
         * @return The topping of the order item.
         */
        public Topping getTopping()
        {
            return this.topping;
        }

        /**
         * Returns the quantity of items to be ordered.
         *
         * @return The quantity of items to be orders.
         */
        public int getQuantity()
        {
            return this.quantity;
        }

        /**
         * Returns the price of a single cupcake with the selected topping and bottom.
         *
         * @return The price of a single cupcake with the selected topping and bottom.
         */
        public int getUnitPrice()
        {
            return this.unitPrice;
        }

        /**
         * Returns the price
         *
         * @return
         */
        public int getTotalPrice()
        {
            return this.unitPrice * quantity;
        }
    }
}
