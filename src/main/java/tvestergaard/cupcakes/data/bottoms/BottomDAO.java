package tvestergaard.cupcakes.data.bottoms;

import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.toppings.Topping;

import java.util.List;

/**
 * Provides CRUD functionality for a persistent storage engine containing {@link Bottom}s.
 */
public interface BottomDAO
{

    /**
     * Returns the {@link Bottom} representing the bottom with the provided {@code id} from the persistent storage source.
     *
     * @param id The id of the bottom to return from the persistent storage source.
     * @return The {@link Bottom} representing the bottom with the provided {@code id} from the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    Bottom get(int id) throws DAOException;

    /**
     * Returns a list of all the bottoms in the persistent storage source.
     *
     * @return The list of all the bottoms in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    List<Bottom> get() throws DAOException;

    /**
     * Inserts a new bottom into the persistent storage source using the provided information.
     *
     * @param name        The name of the bottom to create.
     * @param description The description of the bottom to create.
     * @param price       The price of the bottom to create.
     * @param active      Whether or not the bottom can be ordered.
     * @return The newly created {@link Bottom} representing the inserted bottom in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    Bottom create(String name, String description, int price, boolean active) throws DAOException;

    /**
     * Updates the bottom with the provided id in the persistent storage source, using the provided information.
     *
     * @param id          The id of the bottom to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to.
     * @param active      Whether or not the bottom can be ordered.
     * @return The bottom representing the updated bottom in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    Bottom update(int id, String name, String description, int price, boolean active) throws DAOException;

    /**
     * Deletes the provided {@link Topping} from the persistent storage source.
     *
     * @param topping The topping to delete from the persistent storage source.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    default boolean delete(Topping topping) throws DAOException
    {
        return delete(topping.getId());
    }

    /**
     * Deletes the topping with the provided id from the persistent storage source.
     *
     * @param id The id of the topping to delete from the persistent storage source.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    boolean delete(int id) throws DAOException;
}
