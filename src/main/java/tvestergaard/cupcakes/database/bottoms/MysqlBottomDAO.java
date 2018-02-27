package tvestergaard.cupcakes.database.bottoms;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlBottomDAO extends AbstractMysqlDAO implements BottomDAO
{

	private static final String ID_COLUMN          = "bottoms.id";
	private static final String NAME_COLUMN        = "bottoms.name";
	private static final String DESCRIPTION_COLUMN = "bottoms.description";
	private static final String PRICE_COLUMN       = "bottoms.price";

	public MysqlBottomDAO(MysqlDataSource source)
	{
		super(source);
	}

	@Override public Bottom get(int id)
	{
		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM bottoms WHERE id = ?");
				statement.setInt(1, id);
				results = statement.executeQuery();
				return !results.first() ? null : createBottom(results);

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

	@Override public Bottom get(String name)
	{
		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM bottoms WHERE name = ?");
				statement.setString(1, name);
				results = statement.executeQuery();
				return !results.first() ? null : createBottom(results);

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

	@Override public List<Bottom> get()
	{
		List<Bottom> list = new ArrayList<>();

		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM bottoms");
				results = statement.executeQuery();
				while (results.next())
					list.add(createBottom(results));

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

	Bottom createBottom(ResultSet results) throws SQLException
	{
		return new Bottom(
				results.getInt(ID_COLUMN),
				results.getString(NAME_COLUMN),
				results.getString(DESCRIPTION_COLUMN),
				results.getInt(PRICE_COLUMN)
		);
	}
}
