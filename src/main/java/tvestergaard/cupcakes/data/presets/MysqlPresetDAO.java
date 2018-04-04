package tvestergaard.cupcakes.data.presets;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.data.AbstractMysqlDAO;
import tvestergaard.cupcakes.data.MysqlDAOException;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.data.toppings.Topping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD functionality for a MySQL database containing {@link Preset}s.
 */
public class MysqlPresetDAO extends AbstractMysqlDAO implements PresetDAO
{

    /**
     * Creates a new {@link MysqlPresetDAO}.
     *
     * @param source The {@link MysqlDataSource} serving as the source for the persistent storage being queried.
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
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Preset get(int id) throws MysqlDAOException
    {
        String SQL = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON topping = toppings.id WHERE presets.id = ?";
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                statement.setInt(1, id);
                ResultSet results = statement.executeQuery();
                if (!results.first())
                    return null;

                return createPreset(results);
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Returns all the presets in the database.
     *
     * @return The list of all the presets in the database.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public List<Preset> get() throws MysqlDAOException
    {
        List<Preset> list = new ArrayList<>();
        String sql = "SELECT * FROM presets INNER JOIN bottoms ON bottom = bottoms.id INNER JOIN toppings ON " +
                "topping = toppings.id";

        try {
            try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
                ResultSet results = statement.executeQuery();
                while (results.next())
                    list.add(createPreset(results));

            }
            return list;
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Inserts a new preset into the database.
     *
     * @param name        The name of the preset to insert.
     * @param description The description of the preset to insert.
     * @param bottom      The bottom of the preset to insert.
     * @param topping     The topping of the preset to insert.
     * @return A new {@link Preset} representing the newly inserted row.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public Preset create(String name, String description, Bottom bottom, Topping topping) throws MysqlDAOException
    {
        String SQL = "INSERT INTO presets (`name`, description, bottom, topping) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
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
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
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
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override
    public Preset update(int id, String name, String description, Bottom bottom, Topping topping) throws MysqlDAOException
    {
        String SQL = "UPDATE presets SET `name` = ?, `description` = ?, `bottom` = ?, topping = ? WHERE id = ?";

        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
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
            }
        } catch (SQLException e) {
            throw new MysqlDAOException(e);
        }
    }

    /**
     * Deletes the preset with the provided id.
     *
     * @param id The id of the preset to delete.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws MysqlDAOException When an error occurs during the operation.
     */
    @Override public boolean delete(int id) throws MysqlDAOException
    {

        String SQL = "DELETE FROM presets WHERE id = ?";

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
     * Creates a new {@link Preset} from the current row of the provided {@code ResultSet},
     *
     * @param results The results from which to create the {@link Preset}.
     * @return The newly created instance of {@link Preset}.
     * @throws SQLException
     */
    private Preset createPreset(ResultSet results) throws SQLException
    {
        return new Preset(
                results.getInt("presets.id"),
                results.getString("presets.name"),
                results.getString("presets.description"),
                new Bottom(
                        results.getInt("bottoms.id"),
                        results.getString("bottoms.name"),
                        results.getString("bottoms.description"),
                        results.getInt("bottoms.price"),
                        results.getBoolean("bottoms.active")
                ),
                new Topping(
                        results.getInt("toppings.id"),
                        results.getString("toppings.name"),
                        results.getString("toppings.description"),
                        results.getInt("toppings.price"),
                        results.getBoolean("bottoms.active")
                )
        );
    }
}
