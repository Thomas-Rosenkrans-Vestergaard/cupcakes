package tvestergaard.cupcakes.data.bottoms;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;
import tvestergaard.cupcakes.data.MysqlDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD functionality for a MySQL database containing {@link Bottom}s.
 */
public class MysqlBottomDAO extends AbstractMysqlDAO implements BottomDAO
{

    /**
     * Creates a new {@link MysqlBottomDAO}.
     *
     * @param source The {@link MysqlDataSource} serving as the source for the persistent storage being queried.
     */
    public MysqlBottomDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the {@link Bottom} representing the bottom with the provided {@code id} from the database.
     *
     * @param id The id of the bottom to return from the database.
     * @return The {@link Bottom} representing the bottom with the provided {@code id} from the database.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Bottom get(int id) throws MysqlDAOException
    {
        try {
            String            SQL       = "SELECT * FROM bottoms WHERE id = ?";
            PreparedStatement statement = getConnection().prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            return !results.first() ? null : createBottom(results);
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns a list of all the bottoms in the database.
     *
     * @return The list of all the bottoms in the database.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public List<Bottom> get() throws MysqlDAOException
    {
        String       SQL  = "SELECT * FROM bottoms";
        List<Bottom> list = new ArrayList<>();

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet results = statement.executeQuery();
                while (results.next())
                    list.add(createBottom(results));

                return list;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Inserts a new bottom into the database using the provided information.
     *
     * @param name        The name of the bottom to create.
     * @param description The description of the bottom to create.
     * @param price       The price of the bottom to create.
     * @param active      Whether or not the bottom can be ordered.
     * @return The newly created {@link Bottom} representing the inserted bottom in the database.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Bottom create(String name, String description, int price, boolean active) throws MysqlDAOException
    {
        String SQL = "INSERT INTO bottoms (`name`, description, price, active) VALUES (?, ?, ?, ?)";

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

                return new Bottom(id, name, description, price, active);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Updates the bottom with the provided id in the database, using the provided information.
     *
     * @param id          The id of the bottom to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to.
     * @param active      Whether or not the bottom can be ordered.
     * @return The bottom representing the updated bottom in the database.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Bottom update(int id, String name, String description, int price, boolean active) throws MysqlDAOException
    {
        String SQL = "UPDATE bottoms SET `name` = ?, `description` = ?, `price` = ?, `active` = ? WHERE id = ?";

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

                return new Bottom(id, name, description, price, active);

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Deletes the topping with the provided id from the database.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
     */
    @Override public boolean delete(int id)
    {
        String SQL = "DELETE FROM bottoms WHERE id = ?";

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
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates and returns an instance of {@link Bottom} created from the row pointed to by the provided {@code ResultSet}.
     *
     * @param results The {@code ResultSet} from which to create the new instance of {@link Bottom}.
     * @return The newly created instance of {@link Bottom}.
     * @throws SQLException
     */
    Bottom createBottom(ResultSet results) throws SQLException
    {
        return new Bottom(
                results.getInt("id"),
                results.getString("name"),
                results.getString("description"),
                results.getInt("price"),
                results.getBoolean("active")
        );
    }
}
