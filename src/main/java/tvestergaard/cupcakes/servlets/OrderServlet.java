package tvestergaard.cupcakes.servlets;


import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.ShoppingCart;
import tvestergaard.cupcakes.Utility;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.database.orders.OrderDAO;
import tvestergaard.cupcakes.database.users.MysqlUserDAO;
import tvestergaard.cupcakes.database.users.User;
import tvestergaard.cupcakes.database.users.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.cupcakes.Language.*;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
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
        Notifications  notifications  = new Notifications(request);
        ShoppingCart   shoppingCart   = (ShoppingCart) request.getSession().getAttribute("cart");

        if (!authentication.isAuthenticated()) {
            notifications.error(ERROR_ACCESS_USER);
            response.sendRedirect("login?from=cart");
            return;
        }

        if (shoppingCart.size() == 0) {
            notifications.error(ORDER_NO_ITEMS_ERROR);
            response.sendRedirect("cart");
            return;
        }

        request.setAttribute("user", authentication.getUser());
        request.setAttribute("cart", shoppingCart);
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }

    /**
     * Serves the /preset page where users can see information about the preset with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request, response);
        Notifications  notifications  = new Notifications(request);
        ShoppingCart   shoppingCart   = (ShoppingCart) request.getSession().getAttribute("cart");

        if (!authentication.isAuthenticated()) {
            notifications.error(ERROR_ACCESS_USER);
            response.sendRedirect("login?from=cart");
            return;
        }

        if (shoppingCart.size() == 0) {
            notifications.error(ORDER_NO_ITEMS_ERROR);
            response.sendRedirect("cart");
            return;
        }

        if (shoppingCart.getTotal() > authentication.getUser().getBalance()) {
            notifications.error(ORDER_NOT_ENOUGH_FUNDS);
            response.sendRedirect("funds");
            return;
        }

        try {
            MysqlDataSource source   = new PrimaryDatabase();
            UserDAO         userDAO  = new MysqlUserDAO(source);
            OrderDAO        orderDAO = new MysqlOrderDAO(source);
            orderDAO.create(authentication.getUser(), shoppingCart, request.getParameter("comment"));
            User user = userDAO.get(authentication.getUser().getId());
            notifications.success(ORDER_SUCCESS);
            user = userDAO.update(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBalance() - shoppingCart.getTotal(),
                    user.getRole()
            );

            authentication.updateUser(user);
            shoppingCart.clear();

            response.sendRedirect("profile");

        } catch (Exception e) {
            notifications.error(ORDER_ERROR);
            response.sendRedirect(Utility.referer(request, "cart"));
            return;
        }
    }
}
