package tvestergaard.cupcakes.servlets;


import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Cart;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderServlet",
        urlPatterns = "/order")
public class OrderServlet extends HttpServlet
{

    /**
     * Serves the /order page where users can see information about and confirm their order.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request, response);
        Notifications notifications = new Notifications(request);
        Cart cart = new Cart(request);

        if (!authentication.isAuthenticated()) {
            notifications.error(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login");
            return;
        }

        if (cart.size() == 0) {
            notifications.error("No items is cart.");
            response.sendRedirect("cart");
            return;
        }

        request.setAttribute("user", authentication.getUser());
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }

    /**
     * Serves the /preset page where users can see information about the preset with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        Authentication authentication = new Authentication(request, response);
        Notifications notifications = new Notifications(request);
        Cart cart = new Cart(request);

        if (!authentication.isAuthenticated()) {
            notifications.error(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login");
            return;
        }

        if (cart.size() == 0) {
            notifications.error("No items is cart.");
            response.sendRedirect("cart");
            return;
        }

        try {

            OrderDAO orderDAO = new MysqlOrderDAO(new PrimaryDatabase());
            orderDAO.create(authentication.getUser(), cart, request.getParameter("comment"));
            cart.clear();
            notifications.success("The order was successfully placed.");
            response.sendRedirect("profile");

        } catch (Exception e) {
            notifications.error("The order could not be placed.");
            response.sendRedirect(request.getHeader("referer"));
            return;
        }
    }
}
