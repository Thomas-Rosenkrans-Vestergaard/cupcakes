package tvestergaard.cupcakes.logic;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.bottoms.BottomDAO;
import tvestergaard.cupcakes.data.orders.Order;
import tvestergaard.cupcakes.data.orders.OrderDAO;
import tvestergaard.cupcakes.data.users.User;

import java.util.List;

/**
 * API for performing various operations related to orders.
 */
public class OrderFacade
{

    /**
     * The {@link OrderDAO} providing access the persistent data source for orders.
     */
    private final OrderDAO dao;

    /**
     * Creates a new {@link OrderFacade}.
     *
     * @param dao The {@link BottomDAO} providing access the persistent data source for orders.
     */
    public OrderFacade(OrderDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Returns an {@link Order} representing the order with the provided id in the application.
     *
     * @param id The id of the order to retrieve.
     * @return The {@link Order} representing the order with the provided id in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Order get(int id) throws ApplicationException
    {
        try {
            return dao.get(id);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the orders placed by the provided user.
     *
     * @param user The user to retrieve the orders of.
     * @return The orders placed by the provided user.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public List<Order> get(User user) throws ApplicationException
    {
        try {
            return dao.get(user);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns all the orders in the application.
     *
     * @return A list containing all the orders in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public List<Order> get() throws ApplicationException
    {
        try {
            return dao.get();
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new order into the application using the provided information.
     *
     * @param user    The user placing the order.
     * @param items   The order items to insert.
     * @param comment A comment provided by the user.
     * @return An {@link Order} instance representing the newly inserted row.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Order create(User user, Iterable<ShoppingCart.Item> items, String comment) throws ApplicationException
    {
        try {
            return dao.create(user, items, comment);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the order with the provided id in the application.
     *
     * @param id      The id of the order to update.
     * @param user    The user to update to.
     * @param comment The comment to update to.
     * @param status  The status to update to.
     * @return An entity representing the updated row.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Order update(int id, User user, String comment, Order.Status status) throws ApplicationException
    {
        try {
            return dao.update(id, user, comment, status);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }
}
