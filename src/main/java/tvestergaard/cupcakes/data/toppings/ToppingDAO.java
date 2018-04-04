package tvestergaard.cupcakes.data.toppings;

import tvestergaard.cupcakes.data.DAOException;

import java.util.List;

/**
 * Provides CRUD functionality for a persistent storage engine containing {@link Topping}s.
 */
public interface ToppingDAO
{

    /**
     * Returns an entity representing the topping with the provided id.
     *
     * @param id The id of the topping to return an entity of.
     * @return The entity representing the topping with the provided id.
     * @throws DAOException When an error occurs during the operation.
     */
    Topping get(int id) throws DAOException;

    /**
     * Returns a list of all the toppings in the database.
     *
     * @return The list of all the toppings in the database.
     * @throws DAOException When an error occurs during the operation.
     */
    List<Topping> get() throws DAOException;

    /**
     * Inserts a new topping into the database.
     *
     * @param name        The name of the topping to insert.
     * @param description The description of the topping to insert.
     * @param price       The price of the topping to insert (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return The new entity representing the newly inserted topping.
     * @throws DAOException When an error occurs during the operation.
     */
    Topping create(String name, String description, int price, boolean active) throws DAOException;

    /**
     * Updates the topping with the provided id in the database.
     *
     * @param id          The id of the topping to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return An entity representing the updated row.
     * @throws DAOException When an error occurs during the operation.
     */
    Topping update(int id, String name, String description, int price, boolean active) throws DAOException;

    /**
     * Deletes the row of the provided entity.
     *
     * @param topping The topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    default boolean delete(Topping topping) throws DAOException
    {
        return delete(topping.getId());
    }

    /**
     * Deletes the topping with the provided id.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    boolean delete(int id) throws DAOException;
}
