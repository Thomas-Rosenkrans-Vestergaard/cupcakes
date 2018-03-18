package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.OrderDAO;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Serves the /profile page, where authorized users can view their user details.
 */
@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet
{

    /**
     * The {@link OrderDAO} used to retrieve the orders placed by the user shown.
     */
    private final OrderDAO orderDAO = new MysqlOrderDAO(new PrimaryDatabase());

    /**
     * Serves the /shop page where users can see the products.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAuthenticated()) {
            notifications.warning(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login?from=profile");
            return;
        }

        try {
            request.setAttribute("user", authentication.getUser());
            request.setAttribute("orders", orderDAO.get(authentication.getUser()));
            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}
