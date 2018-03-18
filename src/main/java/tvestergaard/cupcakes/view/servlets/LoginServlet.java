package tvestergaard.cupcakes.view.servlets;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.ShoppingCart;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;
import tvestergaard.cupcakes.view.Parameters;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static tvestergaard.cupcakes.Language.*;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet
{

    /**
     * The {@link UserDAO} used to retrieve user information when users attempt to log in.
     */
    private final UserDAO userDAO = new MysqlUserDAO(new PrimaryDatabase());

    /**
     * Serves the /login page where anonymous users can log in to their user account.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ViewUtilities.attach(request, ViewUtilities.getNotifications(request));
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }

    /**
     * Handles POSTs from the /login page.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = ViewUtilities.getNotifications(request);
        Parameters    parameters    = new Parameters(request);

        if (parameters.notPresent("username") || parameters.notPresent("password")) {
            notifications.error(INCOMPLETE_FORM_POST);
            response.sendRedirect(getErrorLocation(request));
            return;
        }

        try {

            String username = parameters.getString("username");
            String password = parameters.getString("password");

            User   user = userDAO.getFromUsername(username);
            String from = request.getParameter("from");

            if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
                notifications.warning(LOGIN_ERROR);
                response.sendRedirect(getErrorLocation(request));
                return;
            }

            HttpSession session = request.getSession();
            notifications.success(LOGIN_SUCCESS);
            session.setAttribute("user", user);
            session.setAttribute("cart", new ShoppingCart());
            response.sendRedirect(from != null ? from : "profile");

        } catch (Exception e) {
            notifications.error(GENERAL_ERROR);
            response.sendRedirect("profile");
        }
    }

    /**
     * Returns the location to redirect to in case of errors during login POST requests.
     *
     * @param request
     * @return
     */
    private String getErrorLocation(HttpServletRequest request)
    {
        String from = request.getParameter("from");
        if (from == null)
            return "profile";

        return "profile?from=" + from;
    }
}
