package tvestergaard.cupcakes.data.users;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;
import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.MysqlDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD functionality for a MySQL database containing {@link User}s.
 */
public class MysqlUserDAO extends AbstractMysqlDAO implements UserDAO
{

    /**
     * Creates a new {@link MysqlUserDAO}.
     *
     * @param source The {@link MysqlDataSource} serving as the source for the persistent storage being queried.
     */
    public MysqlUserDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a {@link User} object representing the user with the provided id from the persistent data source.
     *
     * @param id The id of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided id. Returns
     * <code>null</code> if the provided id doesn't match a user in the persistent storage source.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public User get(int id) throws MysqlDAOException
    {
        try {

            PreparedStatement statement = null;
            ResultSet         results   = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM users WHERE id = ?");
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
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns a {@link User} object representing the user with the provided username from the persistent data source.
     *
     * @param username The username of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided username. Returns
     * <code>null</code> if the provided username doesn't match a user in the persistent storage source.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public User getFromUsername(String username) throws MysqlDAOException
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
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Retrieves the user with the provided email.
     *
     * @param email The email of the user to return.
     * @return The user with the provided email.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public User getFromEmail(String email) throws MysqlDAOException
    {
        try {

            PreparedStatement statement = null;
            ResultSet         results   = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM users WHERE users.email = ?");
                statement.setString(1, email);
                results = statement.executeQuery();
                return !results.first() ? null : createUser(results);

            } finally {
                if (statement != null)
                    statement.close();
                if (results != null)
                    results.close();
            }

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Creates a new user in the persistent storage source using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email of the user to create.
     * @param password The password of the user to create.
     * @return An {@link User} object representing the newly created user record.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public User create(String username, String email, String password) throws MysqlDAOException
    {
        try {

            String            update     = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

            try {

                statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.first();

                connection.commit();

                return get(generatedKeys.getInt(1));

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Creates a new user in the persistent storage source using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email address of the user to create.
     * @param password The password (hashed) of the user to create.
     * @param balance  The initial balance of the user to create.
     * @param role     The role of the user to create.
     * @return A {@link User} entity representing the newly inserted user.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public User create(String username, String email, String password, int balance, User.Role role) throws MysqlDAOException
    {
        try {

            String            update     = "INSERT INTO users (username, email, password, balance, role) VALUES (?, ?, ?, ?, ?)";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

            try {

                statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setInt(4, balance);
                statement.setInt(5, role.code);

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.first();

                connection.commit();

                return get(generatedKeys.getInt(1));

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Updates the user with the provided id using the provided information.
     *
     * @param id       The id of the user to update.
     * @param username The username to update to.
     * @param email    The email to update to.
     * @param password The password to update to.
     * @param balance  The balance to update to.
     * @param role     The role to update to.
     * @return A {@link User} entity representing the updated user.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public User update(int id, String username, String email, String password, int balance, User.Role role) throws MysqlDAOException
    {
        try {

            String            update     = "UPDATE users SET username = ?, email = ?, password = ?, balance = ?, role = ? WHERE id = ?";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

            try {

                statement = connection.prepareStatement(update);
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setInt(4, balance);
                statement.setInt(5, role.code);
                statement.setInt(6, id);

                statement.executeUpdate();
                connection.commit();

                return new User(id, username, email, password, balance, role);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns a list of all the users in the persistent storage source.
     *
     * @return A list of all the users in the persistent storage source.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public List<User> get() throws MysqlDAOException
    {
        List<User> list = new ArrayList<>();

        try {

            PreparedStatement statement = null;
            ResultSet         results   = null;

            try {

                statement = getConnection().prepareStatement("SELECT * FROM users");
                results = statement.executeQuery();
                while (results.next())
                    list.add(createUser(results));

                return list;

            } finally {
                if (statement != null)
                    statement.close();
                if (results != null)
                    results.close();
            }

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Deletes the user with the provided id from the persistent storage source.
     *
     * @param id The id of the user to delete from the persistent storage source.
     * @return {@code true} if the user record was deleted, {@code false} if the user record was not deleted.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public boolean delete(int id) throws MysqlDAOException
    {
        try {

            String            delete     = "DELETE FROM users WHERE id = ?";
            Connection        connection = getConnection();
            PreparedStatement statement  = null;

            try {

                statement = connection.prepareStatement(delete);
                statement.setInt(1, id);

                int deleted = statement.executeUpdate();
                connection.commit();

                return deleted > 0;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (statement != null)
                    statement.close();
            }

        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    protected User createUser(ResultSet results) throws SQLException
    {
        return new User(
                results.getInt("users.id"),
                results.getString("users.username"),
                results.getString("users.email"),
                results.getString("users.password"),
                results.getInt("users.balance"),
                User.Role.code(results.getInt("users.role"))
        );
    }
}
