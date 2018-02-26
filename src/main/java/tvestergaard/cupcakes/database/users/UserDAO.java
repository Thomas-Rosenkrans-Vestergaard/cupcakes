package tvestergaard.cupcakes.database.users;

public interface UserDAO
{

	/**
	 * Returns a {@link User} object representing the user with the provided <code>id</code>.
	 *
	 * @param id The id of the {@link User} object to return.
	 *
	 * @return The {@link User} object representing the user with the provided <code>id</code>. Returns
	 * <code>null</code> if the provided <code>id</code> doesn't match a user in the database.
	 */
	User findFromUsername(int id);

	/**
	 * Returns a {@link User} object representing the user with the provided <code>username</code>.
	 *
	 * @param username The username of the {@link User} object to return.
	 *
	 * @return The {@link User} object representing the user with the provided <code>username</code>. Returns
	 * <code>null</code> if the provided <code>username</code> doesn't match a user in the database.
	 */
	User findFromUsername(String username);

	/**
	 * Retrieves a {@link User} with the provided <code>email</code>.
	 *
	 * @param email The email of the {@link User} to return.
	 *
	 * @return The {@link User} with the provided <code>email</code>.
	 */
	User findFromEmail(String email);

	/**
	 * Creates a new user in the database using the provided information.
	 *
	 * @param username The username of the user to create.
	 * @param email    The email of the user to create.
	 * @param password The password of the user to create.
	 *
	 * @return An {@link User} object representing the newly created user record.
	 */
	User create(String username, String email, String password);
}
