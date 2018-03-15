package tvestergaard.cupcakes.database.orders;

import tvestergaard.cupcakes.ShoppingCart;
import tvestergaard.cupcakes.database.users.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO
{

    /**
     * Returns an {@link Order} representing the order with the provided id in the database.
     *
     * @param id The id of the order to retrieve.
     * @return The {@link Order} representing the order with the provided id in the database.
     * @throws SQLException
     */
    Order get(int id) throws SQLException;

    /**
     * Returns the orders placed by the provided user.
     *
     * @param user The user to retrieve the orders of.
     * @return The orders placed by the provided user.
     * @throws SQLException
     */
    List<Order> get(User user) throws SQLException;

    /**
     * Returns all the orders in the database.
     *
     * @return A list containing all the orders in the database.
     * @throws SQLException
     */
    List<Order> get() throws SQLException;

    /**
     * Inserts a new order into the database using the provided information.
     *
     * @param user    The user placing the order.
     * @param items   The order items to insert.
     * @param comment A comment provided by the user.
     * @return An {@link Order} instance representing the newly inserted row.
     */
    Order create(User user, Iterable<ShoppingCart.Item> items, String comment) throws SQLException;
}
