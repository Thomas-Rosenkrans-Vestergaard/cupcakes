package tvestergaard.cupcakes.data.presets;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.toppings.Topping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlPresetDAO extends AbstractMysqlDAO implements PresetDAO
{

    /**
     * Creates a new {@link MysqlDataSource}.
     *
     * @param source The source.
     */
    public MysqlPresetDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a {@link Preset} entity representing the preset with the provided id.
     *
     * @param id The id of the preset to retrieve.
     * @return The {@link Preset} entity representing the preset with the provided id.
     * @throws SQLException
     */
    @Override public Preset get(int id) throws SQLException
    {
        String sql = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON " +
                "topping = toppings.id WHERE presets.id = ?";

        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();
        if (!results.first())
            return null;

        Preset preset = createPreset(results);

        results.close();
        statement.close();

        return preset;

    }

    /**
     * Returns all the presets in the database.
     *
     * @return The list of all the presets in the database.
     * @throws SQLException
     */
    @Override public List<Preset> get() throws SQLException
    {
        List<Preset> list = new ArrayList<>();
        String sql = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON " +
                "topping = toppings.id";

        PreparedStatement statement = getConnection().prepareStatement(sql);
        ResultSet results = statement.executeQuery();
        while (results.next())
            list.add(createPreset(results));

        results.close();
        statement.close();

        return list;

    }

    /**
     * Inserts a new preset into the database.
     *
     * @param name        The name of the preset to insert.
     * @param description The description of the preset to insert.
     * @param bottom      The bottom of the preset to insert.
     * @param topping     The topping of the preset to insert.
     * @return A new {@link Preset} representing the newly inserted row.
     * @throws SQLException
     */
    @Override public Preset create(String name, String description, Bottom bottom, Topping topping) throws SQLException
    {
        String update = "INSERT INTO presets (`name`, description, bottom, topping) VALUES (?, ?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, bottom.getId());
            statement.setInt(4, topping.getId());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.first();

            int id = generatedKeys.getInt(1);
            connection.commit();

            return new Preset(id, name, description, bottom, topping);

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (statement != null)
                statement.close();
        }
    }

    /**
     * Updates the preset with the provided id in the database.
     *
     * @param id          The id of the preset to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param bottom      The bottom to update to.
     * @param topping     The topping to update to.
     * @return The entity representing the newly inserted row.
     * @throws SQLException
     */
    @Override
    public Preset update(int id, String name, String description, Bottom bottom, Topping topping) throws SQLException
    {
        String update = "UPDATE presets SET `name` = ?, `description` = ?, `bottom` = ?, topping = ? WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement(update);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, bottom.getId());
            statement.setInt(4, topping.getId());
            statement.setInt(5, id);

            statement.executeUpdate();
            connection.commit();

            return new Preset(id, name, description, bottom, topping);

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (statement != null)
                statement.close();
        }
    }

    /**
     * Deletes the preset with the provided id.
     *
     * @param id The id of the preset to delete.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws SQLException
     */
    @Override public boolean delete(int id) throws SQLException
    {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("DELETE FROM presets WHERE id = ?");
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

    private Preset createPreset(ResultSet resultSet) throws SQLException
    {
        return new Preset(
                resultSet.getInt("presets.id"),
                resultSet.getString("presets.name"),
                resultSet.getString("presets.description"),
                new Bottom(
                        resultSet.getInt("bottoms.id"),
                        resultSet.getString("bottoms.name"),
                        resultSet.getString("bottoms.description"),
                        resultSet.getInt("bottoms.price"),
                        resultSet.getBoolean("bottoms.active")
                ),
                new Topping(
                        resultSet.getInt("toppings.id"),
                        resultSet.getString("toppings.name"),
                        resultSet.getString("toppings.description"),
                        resultSet.getInt("toppings.price"),
                        resultSet.getBoolean("bottoms.active")
                )
        );
    }
}
