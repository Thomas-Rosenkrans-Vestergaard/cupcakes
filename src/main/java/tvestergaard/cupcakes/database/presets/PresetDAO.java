package tvestergaard.cupcakes.database.presets;

import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.toppings.Topping;

import java.sql.SQLException;
import java.util.List;

public interface PresetDAO
{

    /**
     * Returns a {@link Preset} entity representing the preset with the provided id.
     *
     * @param id The id of the preset to retrieve.
     * @return The {@link Preset} entity representing the preset with the provided id.
     * @throws SQLException
     */
    Preset get(int id) throws SQLException;

    /**
     * Returns all the presets in the database.
     *
     * @return The list of all the presets in the database.
     * @throws SQLException
     */
    List<Preset> get() throws SQLException;

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
    Preset create(String name, String description, Bottom bottom, Topping topping) throws SQLException;

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
    Preset update(int id, String name, String description, Bottom bottom, Topping topping) throws SQLException;

    /**
     * Deletes the provided preset from the database.
     *
     * @param preset The preset to delete from the database.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws SQLException
     */
    default boolean delete(Preset preset) throws SQLException
    {
        return delete(preset.getId());
    }

    /**
     * Deletes the preset with the provided id.
     *
     * @param id The id of the preset to delete.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;
}
