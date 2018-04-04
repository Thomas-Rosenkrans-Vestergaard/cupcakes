package tvestergaard.cupcakes.data.toppings;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;
import tvestergaard.cupcakes.data.MysqlDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD functionality for a MySQL database containing {@link Topping}s.
 */
public class MysqlToppingDAO extends AbstractMysqlDAO implements ToppingDAO
{

    /**
     * Creates a new {@link MysqlToppingDAO}.
     *
     * @param source The {@link MysqlDataSource} serving as the source for the persistent storage being queried.
     */
    public MysqlToppingDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns an entity representing the topping with the provided id.
     *
     * @param id The id of the topping to return an entity of.
     * @return The entity representing the topping with the provided id.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Topping get(int id) throws MysqlDAOException
    {
        String SQL = "SELECT * FROM toppings WHERE id = ?";

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                return !results.first() ? null : createTopping(results);

            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns a list of all the toppings in the database.
     *
     * @return The list of all the toppings in the database.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public List<Topping> get() throws MysqlDAOException
    {
        List<Topping> list = new ArrayList<>();
        String        SQL  = "SELECT * FROM toppings";

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet results = statement.executeQuery();
                while (results.next())
                    list.add(createTopping(results));

                return list;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Inserts a new topping into the database.
     *
     * @param name        The name of the topping to insert.
     * @param description The description of the topping to insert.
     * @param price       The price of the topping to insert (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return The new entity representing the newly inserted topping.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Topping create(String name, String description, int price, boolean active) throws MysqlDAOException
    {
        String SQL = "INSERT INTO toppings (`name`, description, price, active) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, price);
                statement.setBoolean(4, active);

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.first();

                int id = generatedKeys.getInt(1);
                connection.commit();

                return new Topping(id, name, description, price, active);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Updates the topping with the provided id in the database.
     *
     * @param id          The id of the topping to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return An entity representing the updated row.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Topping update(int id, String name, String description, int price, boolean active) throws MysqlDAOException
    {
        String SQL = "UPDATE toppings SET `name` = ?, `description` = ?, `price` = ?, `active` = ? WHERE id = ?";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, price);
                statement.setBoolean(4, active);
                statement.setInt(5, id);

                statement.executeUpdate();
                connection.commit();

                return new Topping(id, name, description, price, active);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Deletes the topping with the provided id.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public boolean delete(int id) throws MysqlDAOException
    {
        String SQL = "DELETE FROM toppings WHERE id = ?";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setInt(1, id);

                int deleted = statement.executeUpdate();
                connection.commit();

                return deleted > 0;

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Creates a new {@link Topping} from the current row of the provided {@code ResultSet},
     *
     * @param results The results from which to create the {@link Topping}.
     * @return The newly created instance of {@link Topping}.
     * @throws SQLException
     */
    private Topping createTopping(ResultSet results) throws SQLException
    {
        return new Topping(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description"),
                results.getInt("price"),
                results.getBoolean("active")
        );
    }
}
