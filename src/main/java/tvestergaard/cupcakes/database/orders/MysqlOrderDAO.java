package tvestergaard.cupcakes.database.orders;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.ShoppingCart;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MysqlOrderDAO extends AbstractMysqlDAO implements OrderDAO
{

	public MysqlOrderDAO(MysqlDataSource source)
	{
		super(source);
	}

	@Override public Order get(int id)
	{
		try {

			String sql = "SELECT * FROM orders " +
                         "INNER JOIN order_items ON `orders`.id = `order` " +
						 "INNER JOIN users ON `user` = users.id " +
						 "INNER JOIN bottoms ON bottoms.id = order_items.bottom " +
						 "INNER JOIN toppings ON toppings.id = order_items.topping " +
						 "WHERE orders.id = ?";

			PreparedStatement statement = getConnection().prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet results = statement.executeQuery();
			if (!results.first())
				return null;

			Order order = createOrder(results);

			results.close();
			statement.close();

			return order;

		} catch (Exception e) {
			throw new IllegalStateException(e);
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
						resultSet.getInt("bottoms.price")
				),
				new Topping(
						resultSet.getInt("toppings.id"),
						resultSet.getString("toppings.name"),
						resultSet.getString("toppings.description"),
						resultSet.getInt("toppings.price")
				),
				resultSet.getInt("order_items.amount"),
				resultSet.getInt("order_items.unit_price"),
				resultSet.getInt("order_items.total_price")
		);
	}

	@Override public List<Order> get(User user)
	{
		List<Order> orders = new ArrayList<>();

		try {

			String sql = "SELECT * FROM orders INNER JOIN order_items ON `orders`.id = `order`" +
						 "INNER JOIN users ON `user` = users.id " +
						 "INNER JOIN bottoms ON bottoms.id = order_items.bottom " +
						 "INNER JOIN toppings ON toppings.id = order_items.topping " +
						 "WHERE `user` = ?";

			PreparedStatement statement = getConnection().prepareStatement(sql);
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

			results.close();
			statement.close();

			return orders;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override public List<Order> get()
	{
		List<Order> orders = new ArrayList<>();

		try {

			String sql = "SELECT * FROM orders INNER JOIN order_items ON `orders`.id = `order`" +
						 "INNER JOIN users ON `user` = users.id " +
						 "INNER JOIN bottoms ON bottoms.id = order_items.bottom " +
						 "INNER JOIN toppings ON toppings.id = order_items.topping";

			PreparedStatement statement = getConnection().prepareStatement(sql);
			ResultSet         results   = statement.executeQuery();
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

			results.close();
			statement.close();

			return orders;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override public Order create(User user, ShoppingCart shoppingCart, String comment)
	{
		try {

			String            update     = "INSERT INTO orders (`user`, total, comment) VALUES (?, ?, ?)";
			Connection        connection = getConnection();
			PreparedStatement statement  = null;

			try {

				statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, user.getId());
				statement.setInt(2, shoppingCart.getTotal());
				statement.setString(3, comment);

				statement.executeUpdate();

				ResultSet generatedKeys = statement.getGeneratedKeys();
				generatedKeys.first();

				int                         id       = generatedKeys.getInt(1);
				Iterator<ShoppingCart.Item> iterator = shoppingCart.iterator();

				String sql = "INSERT INTO order_items (`order`, bottom, topping, amount, unit_price, total_price) " +
							 "VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement itemStatement = connection.prepareStatement(sql);

				while (iterator.hasNext()) {
					ShoppingCart.Item item = iterator.next();
					itemStatement.setInt(1, id);
					itemStatement.setInt(2, item.getBottom().getId());
					itemStatement.setInt(3, item.getTopping().getId());
					itemStatement.setInt(4, item.getAmount());
					itemStatement.setInt(5, item.getUnitPrice());
					itemStatement.setInt(6, item.getTotalPrice());
					itemStatement.executeUpdate();
				}

				connection.commit();

				return get(id);

			} catch (SQLException e) {
				connection.rollback();
				throw e;
			} finally {
				if (statement != null)
					statement.close();
			}

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
