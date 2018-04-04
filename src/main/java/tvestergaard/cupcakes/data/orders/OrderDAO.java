package tvestergaard.cupcakes.data.orders;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.logic.ShoppingCart;

import java.util.List;

/**
 * Provides CRUD functionality for a persistent storage engine containing {@link Order}s.
 */
public interface OrderDAO
{

    /**
     * Returns an {@link Order} representing the order with the provided id in the persistent storage source.
     *
     * @param id The id of the order to retrieve.
     * @return The {@link Order} representing the order with the provided id in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    Order get(int id) throws DAOException;

    /**
     * Returns the orders placed by the provided user.
     *
     * @param user The user to retrieve the orders of.
     * @return The orders placed by the provided user.
     * @throws DAOException When an error occurs during the operation.
     */
    List<Order> get(User user) throws DAOException;

    /**
     * Returns all the orders in the persistent storage source.
     *
     * @return A list containing all the orders in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    List<Order> get() throws DAOException;

    /**
     * Inserts a new order into the persistent storage source using the provided information.
     *
     * @param user    The user placing the order.
     * @param items   The order items to insert.
     * @param comment A comment provided by the user.
     * @return An {@link Order} instance representing the newly inserted row.
     * @throws DAOException When an error occurs during the operation.
     */
    Order create(User user, Iterable<ShoppingCart.Item> items, String comment) throws DAOException;

    /**
     * Updates the order with the provided id in the persistent storage source.
     *
     * @param id      The id of the order to update.
     * @param user    The user to update to.
     * @param comment The comment to update to.
     * @param status  The status to update to.
     * @return An entity representing the updated row.
     * @throws DAOException When an error occurs during the operation.
     */
    Order update(int id, User user, String comment, Order.Status status) throws DAOException;
}
