package tvestergaard.cupcakes.database.toppings;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlToppingDAO extends AbstractMysqlDAO implements ToppingDAO
{

	private static final String ID_COLUMN          = "toppings.id";
	private static final String NAME_COLUMN        = "toppings.name";
	private static final String DESCRIPTION_COLUMN = "toppings.description";
	private static final String PRICE_COLUMN       = "toppings.price";

	public MysqlToppingDAO(MysqlDataSource source)
	{
		super(source);
	}

	@Override public Topping get(int id)
	{
		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM toppings WHERE id = ?");
				statement.setInt(1, id);
				results = statement.executeQuery();
				return !results.first() ? null : createTopping(results);

			} finally {
				if (statement != null)
					statement.close();
				if (results != null)
					results.close();
			}

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override public Topping get(String name)
	{
		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM toppings WHERE name = ?");
				statement.setString(1, name);
				results = statement.executeQuery();
				return !results.first() ? null : createTopping(results);

			} finally {
				if (statement != null)
					statement.close();
				if (results != null)
					results.close();
			}

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override public List<Topping> get()
	{
		List<Topping> list = new ArrayList<>();

		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM toppings");
				results = statement.executeQuery();
				while (results.next())
					list.add(createTopping(results));

				return list;

			} finally {
				if (statement != null)
					statement.close();
				if (results != null)
					results.close();
			}

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	Topping createTopping(ResultSet results) throws SQLException
	{
		return new Topping(
				results.getInt(ID_COLUMN),
				results.getString(NAME_COLUMN),
				results.getString(DESCRIPTION_COLUMN),
				results.getInt(PRICE_COLUMN)
		);
	}
}
