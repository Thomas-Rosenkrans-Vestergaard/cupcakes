package tvestergaard.cupcakes.logic;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.data.DAOException;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.data.users.UserDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * API for performing various operations related to users.
 */
public class UserFacade
{

    /**
     * The {@link UserDAO} providing access the persistent data source for users.
     */
    private final UserDAO dao;

    /**
     * Creates a new {@link UserFacade}.
     *
     * @param dao The {@link UserDAO} providing access the persistent data source for users.
     */
    public UserFacade(UserDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Hashes the provided password using the b-crypt hashing function.
     *
     * @param password The password to hash.
     * @return The resulting digest.
     */
    public String hash(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks if the provided username and password matches any in the application.
     *
     * @param username The username to authenticate with.
     * @param password The password to authenticate with.
     * @return The {@link User} instance when the authenticated was successful, otherwise {@code null}.
     */
    public User authenticate(String username, String password)
    {
        try {
            User user = dao.getFromUsername(username);

            if (user == null)
                return null;

            if (BCrypt.checkpw(password, user.getPassword()))
                return user;

            return null;

        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a {@link User} object representing the user with the provided id ApplicationException.
     *
     * @param id The id of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided id. Returns
     * <code>null</code> if the provided id doesn't match a user in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public User get(int id)
    {
        try {
            return dao.get(id);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a {@link User} object representing the user with the provided username ApplicationException.
     *
     * @param username The username of the {@link User} object to return.
     * @return The {@link User} object representing the user with the provided username. Returns
     * <code>null</code> if the provided username doesn't match a user in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public User getFromUsername(String username)
    {
        try {
            return dao.getFromUsername(username);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Retrieves the user with the provided email.
     *
     * @param email The email of the user to return.
     * @return The user with the provided email.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public User getFromEmail(String email)
    {
        try {
            return dao.getFromEmail(email);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a list of all the users in the application.
     *
     * @return A list of all the users in the application.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public List<User> get()
    {
        try {
            return dao.get();
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Creates a new user in the application using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email of the user to create.
     * @param password The password of the user to create.
     * @return An {@link User} object representing the newly created user record.
     * @throws ApplicationException  When an error occurs during the operation.
     * @throws UserCreationException When the provided details could not be created into a new user.
     */
    public User create(String username, String email, String password) throws UserCreationException
    {
        return create(username, email, password, 0, User.Role.USER);
    }

    /**
     * Creates a new user in the application using the provided information.
     *
     * @param username The username of the user to create.
     * @param email    The email address of the user to create.
     * @param password The password (hashed) of the user to create.
     * @param balance  The initial balance of the user to create.
     * @param role     The role of the user to create.
     * @return A {@link User} entity representing the newly inserted user.
     * @throws ApplicationException  When an error occurs during the operation.
     * @throws UserCreationException When the provided details could not be created into a new user.
     */
    public User create(String username, String email, String password, int balance, User.Role role) throws UserCreationException
    {
        try {
            Set<UserCreationException.Reason> reasons = new HashSet<>();

            // Username length
            if (username.length() < 3) {
                reasons.add(UserCreationException.Reason.USERNAME_SHORTER_THAN_3);
            }else if(dao.getFromUsername(username) != null) {
                // Username availability
                reasons.add(UserCreationException.Reason.USERNAME_TAKEN);
            }

            // Email format
            if (!EmailValidator.getInstance().isValid(email))
                reasons.add(UserCreationException.Reason.EMAIL_FORMAT);
            else if(dao.getFromEmail(email) != null) {
                // Email availability
                reasons.add(UserCreationException.Reason.EMAIL_TAKEN);
            }

            // Password length
            if (password.length() < 4)
                reasons.add(UserCreationException.Reason.PASSWORD_SHORTER_THAN_4);

            if (reasons.size() > 0)
                throw new UserCreationException(reasons);

            return dao.create(username, email, hash(password), balance, role);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

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
     * @throws ApplicationException When an error occurs during the operation.
     * @throws UserUpdateException  When the provided information is not legal.
     */
    public User update(int id, String username, String email, String password, int balance, User.Role role) throws UserUpdateException
    {
        try {
            Set<UserUpdateException.Reason> reasons = new HashSet<>();
            User usernameUser;
            User emailUser;

            // Username length
            if (username.length() < 3)
                reasons.add(UserUpdateException.Reason.USERNAME_SHORTER_THAN_3);
            else if((usernameUser = dao.getFromUsername(username)) != null && usernameUser.getId() != id){
                // Username availability
                reasons.add(UserUpdateException.Reason.USERNAME_TAKEN);
            }

            // Email format
            if (!EmailValidator.getInstance().isValid(email))
                reasons.add(UserUpdateException.Reason.EMAIL_FORMAT);
            else if((emailUser = dao.getFromEmail(email)) != null && emailUser.getId() != id){
                // Email availability
                reasons.add(UserUpdateException.Reason.EMAIL_TAKEN);
            }

            // Password length
            if (password.length() < 4)
                reasons.add(UserUpdateException.Reason.PASSWORD_SHORTER_THAN_4);

            if (reasons.size() > 0)
                throw new UserUpdateException(reasons);

            return dao.update(id, username, email, password, balance, role);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Deletes the user with the provided id from the application.
     *
     * @param id The id of the user to delete from the application.
     * @return {@code true} if the user record was deleted, {@code false} if the user record was not deleted.
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

    /**
     * Deletes the user represented by the provided {@link User} from the application.
     *
     * @param user The {@link User} representing the record to delete from the application.
     * @return {@code true} if the user record was deleted, {@code false} if the user record was not deleted.
     * @throws ApplicationException When an error occurs during the operation.
     */
    public boolean delete(User user)
    {
        try {
            return dao.delete(user);
        } catch (DAOException e) {
            throw new ApplicationException(e);
        }
    }
}
