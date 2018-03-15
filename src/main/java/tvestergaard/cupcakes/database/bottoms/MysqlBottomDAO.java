package tvestergaard.cupcakes.database.bottoms;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.database.AbstractMysqlDAO;

import java.sql.*;
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

    /**
     * Returns the {@link Bottom} representing the bottom with the provided {@code id} from the database.
     *
     * @param id The id of the bottom to return from the database.
     * @return The {@link Bottom} representing the bottom with the provided {@code id} from the database.
     * @throws SQLException
     */
    @Override public Bottom get(int id) throws SQLException
    {
        PreparedStatement statement = null;
        ResultSet         results   = null;

        try {

            statement = getConnection().prepareStatement("SELECT * FROM bottoms WHERE id = ?");
            statement.setInt(1, id);
            results = statement.executeQuery();
            return !results.first() ? null : createBottom(results);

        } finally {
            if (statement != null) statement.close();
            if (results != null) results.close();
        }
    }

    /**
     * Returns a list of all the bottoms in the database.
     *
     * @return The list of all the bottoms in the database.
     * @throws SQLException
     */
    @Override public List<Bottom> get() throws SQLException
    {
        List<Bottom>      list      = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet         results   = null;

        try {

            statement = getConnection().prepareStatement("SELECT * FROM bottoms");
            results = statement.executeQuery();
            while (results.next()) list.add(createBottom(results));

            return list;

        } finally {
            if (statement != null) statement.close();
            if (results != null) results.close();
        }
    }

    /**
     * Inserts a new bottom into the database using the provided information.
     *
     * @param name        The name of the bottom to create.
     * @param description The description of the bottom to create.
     * @param price       The price of the bottom to create.
     * @return The newly created {@link Bottom} representing the inserted bottom in the database.
     * @throws SQLException
     */
    @Override public Bottom create(String name, String description, int price) throws SQLException
    {
        String            update     = "INSERT INTO bottoms (`name`, description, price) VALUES (?, ?, ?)";
        Connection        connection = getConnection();
        PreparedStatement statement  = null;

        try {

            statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, price);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.first();

            int id = generatedKeys.getInt(1);
            connection.commit();

            return new Bottom(id, name, description, price);

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
        }
    }

    /**
     * Updates the bottom with the provided id in the database, using the provided information.
     *
     * @param id          The id of the bottom to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to.
     * @return The bottom representing the updated bottom in the database.
     * @throws SQLException
     */
    @Override public Bottom update(int id, String name, String description, int price) throws SQLException
    {
        String            update     = "UPDATE bottoms SET `name` = ?, `description` = ?, `price` = ? WHERE id = ?";
        Connection        connection = getConnection();
        PreparedStatement statement  = null;

        try {

            statement = connection.prepareStatement(update);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, price);
            statement.setInt(4, id);

            statement.executeUpdate();
            connection.commit();

            return new Bottom(id, name, description, price);

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
        }
    }

    /**
     * Deletes the topping with the provided id from the database.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
     * @throws SQLException
     */
    @Override public boolean delete(int id) throws SQLException
    {

        Connection        connection = getConnection();
        PreparedStatement statement  = null;

        try {

            statement = connection.prepareStatement("DELETE FROM bottoms WHERE id = ?");
            statement.setInt(1, id);

            int deleted = statement.executeUpdate();
            connection.commit();

            return deleted > 0;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
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
                results.getInt(ID_COLUMN),
                results.getString(NAME_COLUMN),
                results.getString(DESCRIPTION_COLUMN),
                results.getInt(PRICE_COLUMN)
        );
    }
}
