package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Config;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.users.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet
{

    /**
     * The success message provided when the user is successfully logged out.
     */
    private static final String SUCCESS_NOTIFICATION = "You were successfully logged out, have a nice day.";

    /**
     * The error message provided when the user cannot be logged out.
     */
    private static final String ERROR_NOTIFICATION = "You could not be logged out.";

    /**
     * Logs out the user in the provided session.
     *
     * @param request  The request.
     * @param response The response.
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession   session       = request.getSession();
        User          user          = (User) session.getAttribute(Config.USER_SESSION_KEY);
        Notifications notifications = new Notifications(request);
        String        previous      = request.getHeader("referer");

        if (user == null) {
            notifications.error(ERROR_NOTIFICATION);
            response.sendRedirect(previous);
            return;
        }

        session.setAttribute(Config.USER_SESSION_KEY, null);
        notifications.success(SUCCESS_NOTIFICATION);
        response.sendRedirect(previous);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        logout(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        logout(request, response);
    }
}
