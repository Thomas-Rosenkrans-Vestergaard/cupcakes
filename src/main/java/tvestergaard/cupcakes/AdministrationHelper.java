package tvestergaard.cupcakes;

import tvestergaard.cupcakes.database.users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Provides helpful methods for dealing with the adiministrative pages.
 *
 * @author Thomas
 */
public class AdministrationHelper
{
    private static final String REDIRECT = "../login";

    /**
     * The {@link HttpServletResponse}.
     */
    private final HttpServletResponse response;

    /**
     * The user {@link HttpSession}.
     */
    private final HttpSession session;

    /**
     * The {@link Notifications} to send notifications to the user with.
     */
    private final Notifications messageHelper;

    /**
     * Creates a new {@link AdministrationHelper}.
     *
     * @param request  The {@link HttpServletRequest}.
     * @param response The {@link HttpServletResponse}.
     */
    public AdministrationHelper(HttpServletRequest request, HttpServletResponse response)
    {
        this.response = response;
        this.session = request.getSession();
        this.messageHelper = new Notifications(request);
    }

    /**
     * Checks if the request comes from an administrator.
     *
     * @return True if the request came from an administrator, false otherwise.
     */
    public boolean isAdmin()
    {
        User u = (User) session.getAttribute(Config.USER_SESSION_KEY);

        return u != null && u.getRole() == User.Role.ADMINISTRATOR;
    }

    /**
     * Redirects the user to the login page.
     */
    public void redirect(String referer) throws ServletException, IOException
    {
        messageHelper.warning(Language.ERROR_ACCESS_ADMINISTRATOR);
        response.sendRedirect(REDIRECT + "?referer=" + URLEncoder.encode(referer, "UTF-8"));
    }

    /**
     * Redirects the user to the login page.
     */
    public void redirect() throws ServletException, IOException
    {
        messageHelper.warning(Language.ERROR_ACCESS_ADMINISTRATOR);
        response.sendRedirect(REDIRECT);
    }
}
