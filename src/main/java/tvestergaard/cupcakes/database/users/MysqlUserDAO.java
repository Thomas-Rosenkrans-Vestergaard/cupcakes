package tvestergaard.cupcakes.database.users;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUserDAO extends AbstractMysqlDAO implements UserDAO
{

    public MysqlUserDAO(MysqlDataSource source)
    {
        super(source);
    }

    @Override
    public User find(int id)
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User findFromUsername(String username)
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

    public User findFromEmail(String email)
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
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates a new user in the database using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email of the user to create.
     * @param password The password of the user to create.
     * @return An {@link User} object representing the newly created user record.
     */
    @Override
    public User create(String username, String email, String password)
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

                return find(generatedKeys.getInt(1));

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

    /**
     * Creates a new user in the database using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email of the user to create.
     * @param password The password of the user to create.
     * @return An {@link User} object representing the newly created user record.
     */
    @Override
    public User create(String username, String email, String password, int balance, User.Role role)
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

                return find(generatedKeys.getInt(1));

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

    @Override
    public User update(int id, String username, String email, String password, int balance, User.Role role)
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> get()
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(int id)
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean delete(User user)
    {
        return delete(user.getId());
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
