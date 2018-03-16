package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.cupcakes.Language.LOGOUT_SUCCESS_NOTIFICATION;
import static tvestergaard.cupcakes.Utility.referer;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet
{

    /**
     * Logs out the user the sent the request.
     *
     * @param request  The request.
     * @param response The response.
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request, response);
        Notifications  notifications  = new Notifications(request);

        authentication.logout();
        notifications.success(LOGOUT_SUCCESS_NOTIFICATION);
        response.sendRedirect(referer(request, "shop"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        logout(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        logout(request, response);
    }
}
