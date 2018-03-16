package tvestergaard.cupcakes.servlets;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.Utility;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet
{

    /**
     * Serves the /shop page where users can see the products.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications notifications = new Notifications(request);
        Authentication authentication = new Authentication(request, response);

        if (!authentication.isAuthenticated()) {
            notifications.warning(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login?from=profile");
            return;
        }

        try {

            OrderDAO orderDAO = new MysqlOrderDAO(new PrimaryDatabase());
            request.setAttribute("user", authentication.getUser());
            request.setAttribute("orders", orderDAO.get(authentication.getUser()));
            request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(Utility.referer(request, "shop"));
        }
    }

    /**
     * Serves the /shop page where users can see the products.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
