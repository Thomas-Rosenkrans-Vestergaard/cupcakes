package tvestergaard.cupcakes.data.toppings;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlToppingDAO extends AbstractMysqlDAO implements ToppingDAO
{

    public MysqlToppingDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns an entity representing the topping with the provided id.
     *
     * @param id The id of the topping to return an entity of.
     * @return The entity representing the topping with the provided id.
     * @throws SQLException
     */
    @Override public Topping get(int id) throws SQLException
    {
        PreparedStatement statement = null;
        ResultSet results = null;

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

    }

    /**
     * Returns a list of all the toppings in the database.
     *
     * @return The list of all the toppings in the database.
     * @throws SQLException
     */
    @Override public List<Topping> get() throws SQLException
    {
        List<Topping> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet results = null;

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
    }

    /**
     * Inserts a new topping into the database.
     *
     * @param name        The name of the topping to insert.
     * @param description The description of the topping to insert.
     * @param price       The price of the topping to insert (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return The new entity representing the newly inserted topping.
     * @throws SQLException
     */
    @Override public Topping create(String name, String description, int price, boolean active) throws SQLException
    {
        String update = "INSERT INTO toppings (`name`, description, price, active) VALUES (?, ?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
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
        } finally {
            if (statement != null)
                statement.close();
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
     * @throws SQLException
     */
    @Override public Topping update(int id, String name, String description, int price, boolean active) throws SQLException
    {
        String update = "UPDATE toppings SET `name` = ?, `description` = ?, `price` = ?, `active` = ? WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(update);
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
        } finally {
            if (statement != null)
                statement.close();
        }
    }

    /**
     * Deletes the topping with the provided id.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws SQLException
     */
    @Override public boolean delete(int id) throws SQLException
    {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("DELETE FROM toppings WHERE id = ?");
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
    }

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
