package tvestergaard.cupcakes.view;

import tvestergaard.cupcakes.database.users.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static tvestergaard.cupcakes.database.users.User.Role;

/**
 * Helper class for user authentication operations.
 */
public class Authentication
{

    /**
     * The current session to perform authentication operations upon.
     */
    private final HttpSession session;

    /**
     * Creates a new {@link Authentication}.
     *
     * @param request The request to perform authentication operations upon.
     */
    public Authentication(HttpServletRequest request)
    {
        this.session = request.getSession();
    }

    /**
     * Updates the User object in session to the provided one.
     *
     * @param user The User object to update to.
     */
    public void updateUser(User user)
    {
        session.setAttribute("user", user);
    }

    /**
     * Logs out the user, by deleting the User object from session.
     */
    public void logout()
    {
        session.setAttribute("user", null);
    }

    /**
     * Returns the User object in session.
     *
     * @return The User object in session. {@code null} if the user is not authenticated.
     */
    public User getUser()
    {
        return (User) session.getAttribute("user");
    }

    /**
     * Checks if the user is authenticated (logged in).
     *
     * @return {@code true} when the user is authenticated, {@code false} otherwise.
     */
    public boolean isAuthenticated()
    {
        return session.getAttribute("user") != null;
    }

    /**
     * Checks if the user is authenticated and of a role equal to or greater than the provided Role.
     *
     * @param role The lower bound Role.
     * @return {@code true} of the user is authenticated and of a role equal to or greater than the provided Role,
     * otherwise {@code false}.
     */
    public boolean is(Role role)
    {
        User user = getUser();

        return user != null && user.getRole().code >= role.code;
    }

    /**
     * Checks if the user is an administrator.
     *
     * @return {@code true} if the user is an administrator, otherwise {@code false}.
     */
    public boolean isAdministrator()
    {
        return is(Role.ADMINISTRATOR);
    }

    /**
     * Checks if the user is an owner.
     *
     * @return {@code true} if the user is an owner, otherwise {@code false}.
     */
    public boolean isOwner()
    {
        return is(Role.OWNER);
    }
}
