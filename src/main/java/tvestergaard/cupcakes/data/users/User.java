package tvestergaard.cupcakes.data.users;

import java.util.Objects;

/**
 * Represent a row in the user table.
 */
public class User
{

    /**
     * The id of the user.
     */
    private final int id;

    /**
     * The email address of the user.
     */
    private final String email;

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * The hashed password of the user.
     */
    private final String password;

    /**
     * The balance of the user.
     */
    private final int balance;

    /**
     * The users role.
     */
    private final Role role;

    /**
     * Creates a new user.
     *
     * @param id       The id of the user.
     * @param username The username of the user.
     * @param email    The email of the user.
     * @param password The hashed password of the user.
     * @param balance  The balance of the user.
     * @param role     The role of the user.
     */
    public User(int id, String username, String email, String password, int balance, Role role)
    {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    /**
     * Returns the id of the user.
     *
     * @return The id of the user.
     */
    public final int getId()
    {
        return this.id;
    }

    /**
     * Returns the email address of the user.
     *
     * @return The email address of teh user.
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user.
     */
    public final String getUsername()
    {
        return this.username;
    }

    /**
     * Returns the hashed password of the user.
     *
     * @return The hashed password of the user.
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Returns the balance of the user (in cents).
     *
     * @return The balance of the user (in cents).
     */
    public final int getBalance()
    {
        return this.balance;
    }

    /**
     * Returns the role of the user.
     *
     * @return The role of the user.
     */
    public Role getRole()
    {
        return this.role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
               balance == user.balance &&
               Objects.equals(email, user.email) &&
               Objects.equals(username, user.username);
    }

    public enum Role
    {
        USER(0),
        ADMINISTRATOR(1),
        OWNER(2);

        public final int code;

        Role(int code)
        {
            this.code = code;
        }

        public int getCode()
        {
            return this.code;
        }

        public boolean is(Role role)
        {
            return code >= role.code;
        }

        private static Role[] roles = Role.values();

        public static Role code(int code)
        {
            return roles[code];
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "User{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", username='" + username + '\'' +
               ", balance=" + balance +
               ", role=" + role +
               '}';
    }
}
