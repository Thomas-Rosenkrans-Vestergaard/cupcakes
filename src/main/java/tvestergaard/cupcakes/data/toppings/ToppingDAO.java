package tvestergaard.cupcakes.data.toppings;

import java.sql.SQLException;
import java.util.List;

public interface ToppingDAO
{

    /**
     * Returns an entity representing the topping with the provided id.
     *
     * @param id The id of the topping to return an entity of.
     * @return The entity representing the topping with the provided id.
     * @throws SQLException
     */
    Topping get(int id) throws SQLException;

    /**
     * Returns a list of all the toppings in the database.
     *
     * @return The list of all the toppings in the database.
     * @throws SQLException
     */
    List<Topping> get() throws SQLException;

    /**
     * Inserts a new topping into the database.
     *
     * @param name        The name of the topping to insert.
     * @param description The description of the topping to insert.
     * @param price       The price of the topping to insert (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return The new entity representing the newly inserted topping.
     * @throws SQLException
     */
    Topping create(String name, String description, int price, boolean active) throws SQLException;

    /**
     * Updates the topping with the provided id in the database.
     *
     * @param id          The id of the topping to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to (in cents).
     * @param active      Whether or not the topping can be ordered.
     * @return An entity representing the updated row.
     * @throws SQLException
     */
    Topping update(int id, String name, String description, int price, boolean active) throws SQLException;

    /**
     * Deletes the row of the provided entity.
     *
     * @param topping The topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws SQLException
     */
    default boolean delete(Topping topping) throws SQLException
    {
        return delete(topping.getId());
    }

    /**
     * Deletes the topping with the provided id.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} if the row was successfully deleted, {@code false} if the row was not deleted.
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;
}
