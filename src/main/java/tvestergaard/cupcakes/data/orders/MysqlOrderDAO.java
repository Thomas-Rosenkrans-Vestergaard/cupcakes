package tvestergaard.cupcakes.data.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;
import tvestergaard.cupcakes.data.MysqlDAOException;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.logic.ShoppingCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD functionality for a MySQL database containing {@link Order}s.
 */
public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO
{

    /**
     * Creates a new {@link MysqlOrderDAO}.
     *
     * @param source The {@link MysqlDataSource} serving as the source for the persistent storage being queried.
     */
    public MysqlOrderDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns an {@link Order} representing the order with the provided id in the persistent storage source.
     *
     * @param id The id of the order to retrieve.
     * @return The {@link Order} representing the order with the provided id in the persistent storage source.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Order get(int id) throws MysqlDAOException
    {
        String SQL = "SELECT *, (SELECT SUM(unit_price * quantity) FROM order_items WHERE order_items.`order` = orders.id) as `orders.total` " +
                "FROM orders INNER JOIN order_items ON `orders`.id = `order`" +
                "INNER JOIN users ON `user` = users.id " +
                "INNER JOIN bottoms ON bottoms.id = order_items.bottom " +
                "INNER JOIN toppings ON toppings.id = order_items.topping " +
                "WHERE orders.id = ?";

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (!results.first())
                    return null;

                Order order = createOrder(results);

                return order;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns the orders placed by the provided user.
     *
     * @param user The user to retrieve the orders of.
     * @return The orders placed by the provided user.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public List<Order> get(User user) throws MysqlDAOException
    {
        List<Order> orders = new ArrayList<>();
        String SQL = "SELECT *, (SELECT SUM(unit_price * quantity) FROM order_items WHERE order_items.`order` = orders.id) as `orders.total` " +
                "FROM orders INNER JOIN order_items ON `orders`.id = `order`" +
                "INNER JOIN users ON `user` = users.id " +
                "INNER JOIN bottoms ON bottoms.id = order_items.bottom " +
                "INNER JOIN toppings ON toppings.id = order_items.topping " +
                "WHERE `user` = ?";

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                statement.setInt(1, user.getId());
                ResultSet results = statement.executeQuery();
                if (!results.first())
                    return null;

                int currentId = results.getInt("orders.id");
                orders.add(createOrder(results));
                while (results.next()) {
                    if (currentId != results.getInt("orders.id")) {
                        currentId = results.getInt("orders.id");
                        orders.add(createOrder(results));
                    }
                }

                return orders;

            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns all the orders in the persistent storage source.
     *
     * @return A list containing all the orders in the persistent storage source.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public List<Order> get() throws MysqlDAOException
    {
        List<Order> orders = new ArrayList<>();
        String SQL = "SELECT *, (SELECT SUM(unit_price * quantity) FROM order_items WHERE order_items.`order` = orders.id) as `orders.total` " +
                "FROM orders INNER JOIN order_items ON `orders`.id = `order`" +
                "INNER JOIN users ON `user` = users.id " +
                "INNER JOIN bottoms ON bottoms.id = order_items.bottom " +
                "INNER JOIN toppings ON toppings.id = order_items.topping";

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet results = statement.executeQuery();
                if (!results.first())
                    return null;

                int currentId = results.getInt("orders.id");
                orders.add(createOrder(results));
                while (results.next()) {
                    if (currentId != results.getInt("orders.id")) {
                        currentId = results.getInt("orders.id");
                        orders.add(createOrder(results));
                    }
                }
            }
            return orders;

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Inserts a new order into the persistent storage source using the provided information.
     *
     * @param user    The user placing the order.
     * @param items   The order items to insert.
     * @param comment A comment provided by the user.
     * @return An {@link Order} instance representing the newly inserted row.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Order create(User user, Iterable<ShoppingCart.Item> items, String comment) throws MysqlDAOException
    {
        String SQL = "INSERT INTO orders (`user`, comment) VALUES (?, ?)";
        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, user.getId());
                statement.setString(2, comment);

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.first();

                int               id            = generatedKeys.getInt(1);
                String            sql           = "INSERT INTO order_items (`order`, bottom, topping, quantity, unit_price) " + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement itemStatement = connection.prepareStatement(sql);

                for (ShoppingCart.Item item : items) {
                    itemStatement.setInt(1, id);
                    itemStatement.setInt(2, item.getBottom().getId());
                    itemStatement.setInt(3, item.getTopping().getId());
                    itemStatement.setInt(4, item.getQuantity());
                    itemStatement.setInt(5, item.getUnitPrice());
                    itemStatement.executeUpdate();
                }

                connection.commit();

                return get(id);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Updates the order with the provided id in the persistent storage source.
     *
     * @param id      The id of the order to update.
     * @param user    The user to update to.
     * @param comment The comment to update to.
     * @param status  The status to update to.
     * @return An entity representing the updated row.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Order update(int id, User user, String comment, Order.Status status) throws MysqlDAOException
    {
        String SQL = "UPDATE orders  SET `user` = ?, comment = ?, status = ? WHERE id = ?";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, user.getId());
                statement.setString(2, comment);
                statement.setInt(3, status.getCode());
                statement.setInt(4, id);

                statement.executeUpdate();
                connection.commit();

                return get(id);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    private Order createOrder(ResultSet results) throws SQLException
    {
        return new Order(
                results.getInt("orders.id"),
                new User(
                        results.getInt("users.id"),
                        results.getString("users.username"),
                        results.getString("users.email"),
                        results.getString("users.password"),
                        results.getInt("users.balance"),
                        User.Role.code(results.getInt("users.role"))
                ),
                results.getInt("orders.total"),
                results.getString("orders.comment"),
                Order.Status.fromCode(results.getInt("orders.status")),
                createOrderItems(results)
        );
    }

    private List<Order.Item> createOrderItems(ResultSet resultSet) throws SQLException
    {
        List<Order.Item> items = new ArrayList<>();

        int currentOrder = resultSet.getInt("orders.id");
        items.add(createItem(resultSet));

        while (resultSet.next()) {
            if (resultSet.getInt("orders.id") == currentOrder)
                items.add(createItem(resultSet));
            else {
                resultSet.previous();
                return items;
            }
        }

        return items;
    }

    private Order.Item createItem(ResultSet resultSet) throws SQLException
    {
        return new Order.Item(
                resultSet.getInt("order_items.id"),
                new Bottom(
                        resultSet.getInt("bottoms.id"),
                        resultSet.getString("bottoms.name"),
                        resultSet.getString("bottoms.description"),
                        resultSet.getInt("bottoms.price"),
                        resultSet.getBoolean("bottoms.active")
                ),
                new Topping(
                        resultSet.getInt("toppings.id"),
                        resultSet.getString("toppings.name"),
                        resultSet.getString("toppings.description"),
                        resultSet.getInt("toppings.price"),
                        resultSet.getBoolean("toppings.active")
                ),
                resultSet.getInt("order_items.quantity"),
                resultSet.getInt("order_items.unit_price")
        );
    }
}
