package tvestergaard.cupcakes.logic;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.bottoms.Bottom;
import tvestergaard.cupcakes.data.bottoms.BottomDAO;
import tvestergaard.cupcakes.data.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.data.toppings.Topping;

import java.util.List;

/**
 * API for performing various operations related to bottoms.
 */
public class BottomFacade
{

    /**
     * The {@link BottomDAO} providing access the persistent data source for bottoms.
     */
    private final BottomDAO dao;

    /**
     * Creates a new {@link BottomFacade}.
     *
     * @param dao The {@link BottomDAO} providing access the persistent data source for bottoms.
     */
    public BottomFacade(BottomDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Creates a new {@link BottomFacade} using a {@link MysqlBottomDAO} to access persistent storage and the
     * default {@link com.mysql.cj.jdbc.MysqlDataSource} from {@link ProductionDatabaseSource#get()}.
     */
    public BottomFacade()
    {
        this(new MysqlBottomDAO(ProductionDatabaseSource.get()));
    }

    /**
     * Returns the {@link Bottom} representing the bottom with the provided {@code id} from the application.
     *
     * @param id The id of the bottom to return from the application.
     * @return The {@link Bottom} representing the bottom with the provided {@code id} from the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Bottom get(int id)
    {
        try {
            return dao.get(id);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a list of all the bottoms in the application.
     *
     * @return The list of all the bottoms in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public List<Bottom> get()
    {
        try {
            return dao.get();
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new bottom into the application using the provided information.
     *
     * @param name        The name of the bottom to create.
     * @param description The description of the bottom to create.
     * @param price       The price of the bottom to create.
     * @param active      Whether or not the bottom can be ordered.
     * @return The newly created {@link Bottom} representing the inserted bottom in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Bottom create(String name, String description, int price, boolean active)
    {
        try {
            return dao.create(name, description, price, active);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the bottom with the provided id in the application, using the provided information.
     *
     * @param id          The id of the bottom to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to.
     * @param active      Whether or not the bottom can be ordered.
     * @return The bottom representing the updated bottom in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public Bottom update(int id, String name, String description, int price, boolean active)
    {
        try {
            return dao.update(id, name, description, price, active);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Deletes the provided {@link Topping} from the application.
     *
     * @param topping The topping to delete from the application.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
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
     * Deletes the topping with the provided id from the application.
     *
     * @param id The id of the topping to delete from the application.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
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
