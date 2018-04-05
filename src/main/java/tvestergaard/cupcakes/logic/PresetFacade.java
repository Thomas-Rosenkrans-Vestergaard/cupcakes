package tvestergaard.cupcakes.logic;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.presets.MysqlPresetDAO;
import tvestergaard.cupcakes.data.presets.Preset;
import tvestergaard.cupcakes.data.presets.PresetDAO;
import tvestergaard.cupcakes.data.toppings.Topping;

import java.util.List;

/**
 * API for performing various operations related to orders.
 */
public class PresetFacade
{

    /**
     * The {@link PresetDAO} providing access the persistent data source for presets.
     */
    private final PresetDAO dao;

    /**
     * Creates a new {@link PresetFacade}.
     *
     * @param dao The {@link PresetDAO} providing access the persistent data source for presets.
     */
    public PresetFacade(PresetDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Creates a new {@link PresetFacade} using a {@link MysqlPresetDAO} to access persistent storage and the
     * default {@link com.mysql.cj.jdbc.MysqlDataSource} from {@link ProductionDatabaseSource#get()}.
     */
    public PresetFacade()
    {
        this(new MysqlPresetDAO(ProductionDatabaseSource.get()));
    }

    /**
     * Returns a {@link Preset} entity representing the preset with the provided id.
     *
     * @param id The id of the preset to retrieve.
     * @return The {@link Preset} entity representing the preset with the provided id.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Preset get(int id)
    {
        try {
            return dao.get(id);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns all the presets in the persistent storage source.
     *
     * @return The list of all the presets in the persistent storage source.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public List<Preset> get()
    {
        try {
            return dao.get();
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new preset into the persistent storage source.
     *
     * @param name        The name of the preset to insert.
     * @param description The description of the preset to insert.
     * @param bottom      The bottom of the preset to insert.
     * @param topping     The topping of the preset to insert.
     * @return A new {@link Preset} representing the newly inserted row.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Preset create(String name, String description, Bottom bottom, Topping topping)
    {
        try {
            return dao.create(name, description, bottom, topping);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the preset with the provided id in the persistent storage source.
     *
     * @param id          The id of the preset to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param bottom      The bottom to update to.
     * @param topping     The topping to update to.
     * @return The entity representing the newly inserted row.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Preset update(int id, String name, String description, Bottom bottom, Topping topping)
    {
        try {
            return dao.update(id, name, description, bottom, topping);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Deletes the provided preset from the persistent storage source.
     *
     * @param preset The preset to delete from the persistent storage source.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public boolean delete(Preset preset)
    {
        try {
            return dao.delete(preset);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Deletes the preset with the provided id.
     *
     * @param id The id of the preset to delete.
     * @return {@code true} if the preset is deleted, {@code false} if the preset was not deleted.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public boolean delete(int id)
    {
        try {
            return dao.delete(id);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }
}
