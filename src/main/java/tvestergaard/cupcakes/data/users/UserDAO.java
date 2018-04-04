package tvestergaard.cupcakes.data.users;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO
{

    /**
     * Returns a {@link User} object representing the user with the provided <code>id</code>.
     *
     * @param id The id of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided <code>id</code>. Returns
     * <code>null</code> if the provided <code>id</code> doesn't match a user in the database.
     */
    User get(int id);

    /**
     * Returns a {@link User} object representing the user with the provided <code>username</code>.
     *
     * @param username The username of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided <code>username</code>. Returns
     * <code>null</code> if the provided <code>username</code> doesn't match a user in the database.
     */
    User getFromUsername(String username);

    /**
     * Retrieves the user with the provided email.
     *
     * @param email The email of the user to return.
     * @return The user with the provided email.
     */
    User getFromEmail(String email);

    /**
     * Returns a list of all the users in the database.
     *
     * @return A list of all the users in the database.
     * @throws SQLException
     */
    List<User> get() throws SQLException;

    /**
     * Creates a new user in the database using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email of the user to create.
     * @param password The password of the user to create.
     * @return An {@link User} object representing the newly created user record.
     * @throws SQLException
     */
    User create(String username, String email, String password) throws SQLException;

    /**
     * Creates a new user in the database using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email address of the user to create.
     * @param password The password (hashed) of the user to create.
     * @param balance  The initial balance of the user to create.
     * @param role     The role of the user to create.
     * @return A {@link User} entity representing the newly inserted user.
     * @throws SQLException
     */
    User create(String username, String email, String password, int balance, User.Role role) throws SQLException;

    /**
     * Updates the user with the provided id using the provided information.
     *
     * @param id       The id of the user to update.
     * @param username The username to update to.
     * @param email    The email to update to.
     * @param password The password to update to.
     * @param balance  The balance to update to.
     * @param role     The role to update to.
     * @return A {@link User} entity representing the updated user.
     * @throws SQLException
     */
    User update(int id, String username, String email, String password, int balance, User.Role role) throws SQLException;

    /**
     * Deletes the user with the provided id from the database.
     *
     * @param id The id of the user to delete from the database.
     * @return {@code true} if the user record was deleted, {@code false} if the user record was not deleted.
     */
    boolean delete(int id);

    default boolean delete(User user)
    {
        return delete(user.getId());
    }
}
