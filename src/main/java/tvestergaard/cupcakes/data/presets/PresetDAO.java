package tvestergaard.cupcakes.data.presets;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.toppings.Topping;

import java.util.List;

/**
 * Provides CRUD functionality for a persistent storage engine containing {@link Preset}s.
 */
public interface PresetDAO
{

    /**
     * Returns a {@link Preset} entity representing the preset with the provided id.
     *
     * @param id The id of the preset to retrieve.
     * @return The {@link Preset} entity representing the preset with the provided id.
     * @throws DAOException When an error occurs during the operation.
     */
    Preset get(int id) throws DAOException;

    /**
     * Returns all the presets in the persistent storage source.
     *
     * @return The list of all the presets in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    List<Preset> get() throws DAOException;

    /**
     * Inserts a new preset into the persistent storage source.
     *
     * @param name        The name of the preset to insert.
     * @param description The description of the preset to insert.
     * @param bottom      The bottom of the preset to insert.
     * @param topping     The topping of the preset to insert.
     * @return A new {@link Preset} representing the newly inserted row.
     * @throws DAOException When an error occurs during the operation.
     */
    Preset create(String name, String description, Bottom bottom, Topping topping) throws DAOException;

    /**
     * Updates the preset with the provided id in the persistent storage source.
     *
     * @param id          The id of the preset to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param bottom      The bottom to update to.
     * @param topping     The topping to update to.
     * @return The entity representing the newly inserted row.
     * @throws DAOException When an error occurs during the operation.
     */
    Preset update(int id, String name, String description, Bottom bottom, Topping topping) throws DAOException;

    /**
     * Deletes the provided preset from the persistent storage source.
     *
     * @param preset The preset to delete from the persistent storage source.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    default boolean delete(Preset preset) throws DAOException
    {
        return delete(preset.getId());
    }

    /**
     * Deletes the preset with the provided id.
     *
     * @param id The id of the preset to delete.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    boolean delete(int id) throws DAOException;
}
