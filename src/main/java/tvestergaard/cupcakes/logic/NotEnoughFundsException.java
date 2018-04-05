package tvestergaard.cupcakes.logic;


import tvestergaard.cupcakes.data.orders.Order;
import tvestergaard.cupcakes.data.users.User;

public class NotEnoughFundsException extends Exception
{

    /**
     * The order that was attempted placed.
     */
    private final Order order;

    /**
     * The user who attempted to place the order.
     */
    private final User user;

    /**
     * The amount of funds required (cents).
     */
    private final int required;

    /**
     * The actual number of funds (cents).
     */
    private final int actual;

    /**
     * Creates a new {@link NotEnoughFundsException}.
     *
     * @param order    The order that was attempted placed.
     * @param user     The user who attempted to place the order.
     * @param required The amount of funds required (cents).
     * @param actual   The actual number of funds (cents).
     */
    public NotEnoughFundsException(Order order, User user, int required, int actual)
    {
        this.order = order;
        this.user = user;
        this.required = required;
        this.actual = actual;
    }

    /**
     * Returns the order that was attempted placed.
     *
     * @return The order that was attempted placed.
     */
    public Order getOrder()
    {
        return this.order;
    }

    /**
     * Returns the user who attempted to place the order.
     *
     * @return The user who attempted to place the order.
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * Returns the amount of funds required (cents).
     *
     * @return The amount of funds required (cents).
     */
    public int getRequired()
    {
        return this.required;
    }

    /**
     * Returns the actual number of funds (cents).
     *
     * @return The actual number of funds (cents).
     */
    public int getActual()
    {
        return this.actual;
    }
}
