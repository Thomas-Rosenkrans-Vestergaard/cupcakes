package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.view.Authentication;
import tvestergaard.cupcakes.logic.Notifications;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.cupcakes.logic.Language.LOGOUT_SUCCESS_NOTIFICATION;
import static tvestergaard.cupcakes.view.ViewUtilities.referer;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet
{

    /**
     * Logs out the current user.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request);
        Notifications  notifications  = ViewUtilities.getNotifications(request);

        authentication.logout();
        notifications.success(LOGOUT_SUCCESS_NOTIFICATION);
        response.sendRedirect(referer(request, "shop"));
    }
}
