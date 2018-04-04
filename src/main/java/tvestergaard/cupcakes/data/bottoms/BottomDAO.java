package tvestergaard.cupcakes.data.bottoms;

import tvestergaard.cupcakes.data.toppings.Topping;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides CRUD functionality for the bottoms table.
 */
public interface BottomDAO
{

    /**
     * Returns the {@link Bottom} representing the bottom with the provided {@code id} from the database.
     *
     * @param id The id of the bottom to return from the database.
     * @return The {@link Bottom} representing the bottom with the provided {@code id} from the database.
     * @throws SQLException
     */
    Bottom get(int id) throws SQLException;

    /**
     * Returns a list of all the bottoms in the database.
     *
     * @return The list of all the bottoms in the database.
     * @throws SQLException
     */
    List<Bottom> get() throws SQLException;

    /**
     * Inserts a new bottom into the database using the provided information.
     *
     * @param name        The name of the bottom to create.
     * @param description The description of the bottom to create.
     * @param price       The price of the bottom to create.
     * @param active      Whether or not the bottom can be ordered.
     * @return The newly created {@link Bottom} representing the inserted bottom in the database.
     * @throws SQLException
     */
    Bottom create(String name, String description, int price, boolean active) throws SQLException;

    /**
     * Updates the bottom with the provided id in the database, using the provided information.
     *
     * @param id          The id of the bottom to update.
     * @param name        The name to update to.
     * @param description The description to update to.
     * @param price       The price to update to.
     * @param active      Whether or not the bottom can be ordered.
     * @return The bottom representing the updated bottom in the database.
     * @throws SQLException
     */
    Bottom update(int id, String name, String description, int price, boolean active) throws SQLException;

    /**
     * Deletes the provided {@link Topping} from the database.
     *
     * @param topping The topping to delete from the database.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
     * @throws SQLException
     */
    default boolean delete(Topping topping) throws SQLException
    {
        return delete(topping.getId());
    }

    /**
     * Deletes the topping with the provided id from the database.
     *
     * @param id The id of the topping to delete from the database.
     * @return {@code true} when the record was successfully deleted, {@code false} when the record was not deleted.
     * @throws SQLException
     */
    boolean delete(int id) throws SQLException;
}
