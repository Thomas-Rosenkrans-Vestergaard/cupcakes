package tvestergaard.cupcakes.data.users;

import tvestergaard.cupcakes.data.DAOException;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides CRUD functionality for a persistent storage source containing {@link User}s.
 */
public interface UserDAO
{

    /**
     * Returns a {@link User} object representing the user with the provided id from the persistent data source.
     *
     * @param id The id of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided id. Returns
     * <code>null</code> if the provided id doesn't match a user in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    User get(int id) throws DAOException;

    /**
     * Returns a {@link User} object representing the user with the provided username from the persistent data source.
     *
     * @param username The username of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided username. Returns
     * <code>null</code> if the provided username doesn't match a user in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    User getFromUsername(String username) throws DAOException;

    /**
     * Retrieves the user with the provided email.
     *
     * @param email The email of the user to return.
     * @return The user with the provided email.
     * @throws DAOException When an error occurs during the operation.
     */
    User getFromEmail(String email) throws DAOException;

    /**
     * Returns a list of all the users in the persistent storage source.
     *
     * @return A list of all the users in the persistent storage source.
     * @throws DAOException When an error occurs during the operation.
     */
    List<User> get() throws DAOException;

    /**
     * Creates a new user in the persistent storage source using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email of the user to create.
     * @param password The password of the user to create.
     * @return An {@link User} object representing the newly created user record.
     * @throws DAOException When an error occurs during the operation.
     */
    User create(String username, String email, String password) throws DAOException;

    /**
     * Creates a new user in the persistent storage source using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email address of the user to create.
     * @param password The password (hashed) of the user to create.
     * @param balance  The initial balance of the user to create.
     * @param role     The role of the user to create.
     * @return A {@link User} entity representing the newly inserted user.
     * @throws DAOException When an error occurs during the operation.
     */
    User create(String username, String email, String password, int balance, User.Role role) throws DAOException;

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
     * @throws DAOException When an error occurs during the operation.
     */
    User update(int id, String username, String email, String password, int balance, User.Role role) throws DAOException;

    /**
     * Deletes the user with the provided id from the persistent storage source.
     *
     * @param id The id of the user to delete from the persistent storage source.
     * @return {@code true} if the user record was deleted, {@code false} if the user record was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    boolean delete(int id) throws DAOException;

    /**
     * Deletes the user represented by the provided {@link User} from the persistent storage source.
     *
     * @param user The {@link User} representing the record to delete from the persistent storage source.
     * @return {@code true} if the user record was deleted, {@code false} if the user record was not deleted.
     * @throws DAOException When an error occurs during the operation.
     */
    default boolean delete(User user) throws DAOException
    {
        return delete(user.getId());
    }
}
