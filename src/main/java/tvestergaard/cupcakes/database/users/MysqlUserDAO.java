package tvestergaard.cupcakes.database.users;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.*;

public class MysqlUserDAO extends AbstractMysqlDAO implements UserDAO
{

	public MysqlUserDAO(MysqlDataSource source)
	{
		super(source);
	}

	@Override public User read(int id)
	{
		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM users WHERE users.id = ?");
				statement.setInt(1, id);
				results = statement.executeQuery();
				return !results.first() ? null : createUser(results);

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

	@Override public User read(String username)
	{
		try {

			PreparedStatement statement = null;
			ResultSet         results   = null;

			try {

				statement = getConnection().prepareStatement("SELECT * FROM users WHERE users.username = ?");
				statement.setString(1, username);
				results = statement.executeQuery();
				return !results.first() ? null : createUser(results);

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

	/**
	 * Creates a new user in the database using the provided information.
	 *
	 * @param username The username of the user to create.
	 * @param email    The email of the user to create.
	 * @param password The password of the user to create.
	 *
	 * @return An {@link User} object representing the newly created user record.
	 */
	@Override public User create(String username, String email, String password)
	{

		try {

			String            update     = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
			Connection        connection = getConnection();
			PreparedStatement statement  = null;

			try {

				statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, username);
				statement.setString(2, email);
				statement.setString(3, BCrypt.hashpw(password, BCrypt.gensalt(10)));

				statement.executeUpdate();

				ResultSet generatedKeys = statement.getGeneratedKeys();
				generatedKeys.first();

				connection.commit();

				return read(generatedKeys.getInt(1));

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

	protected User createUser(ResultSet results) throws SQLException
	{
		return new User(
				results.getInt("users.id"),
				results.getString("users.email"),
				results.getString("users.username"),
				results.getInt("users.balance")
		);
	}
}
