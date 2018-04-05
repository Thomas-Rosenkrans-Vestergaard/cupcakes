package tvestergaard.cupcakes.logic;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.data.toppings.Topping;
import tvestergaard.cupcakes.data.toppings.ToppingDAO;

import java.util.List;

/**
 * API for performing various operations related to toppings.
 */
public class ToppingFacade
{

    /**
     * The {@link ToppingDAO} providing access the persistent data source for toppings.
     */
    private final ToppingDAO dao;

    /**
     * Creates a new {@link ToppingFacade}.
     *
     * @param dao The {@link ToppingDAO} providing access the persistent data source for toppings.
     */
    public ToppingFacade(ToppingDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Creates a new {@link ToppingFacade} using a {@link MysqlToppingDAO} to access persistent storage and the
     * default {@link com.mysql.cj.jdbc.MysqlDataSource} from {@link ProductionDatabaseSource#get()}.
     */
    public ToppingFacade()
    {
        this(new MysqlToppingDAO(ProductionDatabaseSource.get()));
    }

    /**
     * Returns an entity representing the topping with the provided id.
     *
     * @param id The id of the topping to return an entity of.
     * @return The entity representing the topping with the provided id.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Topping get(int id)
    {
        try {
            return dao.get(id);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a list of all the toppings in the database.
     *
     * @return The list of all the toppings in the database.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public List<Topping> get()
    {
        try {
            return dao.get();
        } catch (DAOException e) {
            throw new ApplicationException(e);
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
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Topping create(String name, String description, int price, boolean active)
    {
        try {
            return dao.create(name, description, price, active);
        } catch (DAOException e) {
            throw new ApplicationException(e);
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
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Topping update(int id, String name, String description, int price, boolean active)
    {
        try {
            return dao.update(id, name, description, price, active);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Deletes the row of the provided entity.
     *
     * @param topping The topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public boolean delete(Topping topping)
    {
        try {
            return dao.delete(topping);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Deletes the topping with the provided id.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
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
