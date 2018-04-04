package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.logic.*;
import tvestergaard.cupcakes.view.Authentication;
import tvestergaard.cupcakes.data.ProductionDatabaseSource;
import tvestergaard.cupcakes.data.orders.MysqlOrderDAO;
import tvestergaard.cupcakes.data.users.MysqlUserDAO;
import tvestergaard.cupcakes.data.users.User;
import tvestergaard.cupcakes.view.Language;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.cupcakes.view.Language.ERROR_ACCESS_USER;

/**
 * Serves the /order page where users can place orders. Handles the form data submitted from the /order page.
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet
{

    /**
     * Message provided to users attempting to order with an empty cart.
     */
    private final static String ORDER_NO_ITEMS_ERROR = "Your cart is currently empty, you cannot place an order with an empty cart.";

    /**
     * Message provided to users with not enough funds.
     */
    private final static String ORDER_NOT_ENOUGH_FUNDS = "You do not have enough funds to order the products in your cart.";

    /**
     * Message provided to users when their order was successfully placed.
     */
    private final static String ORDER_SUCCESS = "The order was successfully placed.";

    /**
     * Message provided to users when their order could not be placed.
     */
    private final static String ORDER_ERROR = "The order could not be placed.";

    /**
     * Facade for performing various operations related to users.
     */
    private final UserFacade userFacade = new UserFacade(new MysqlUserDAO(ProductionDatabaseSource.singleton()));

    /**
     * Facade for performing various operations related to orders.
     */
    private final OrderFacade orderFacade = new OrderFacade(new MysqlOrderDAO(ProductionDatabaseSource.singleton()));

    /**
     * Serves the /order page where users can see information about and confirm their order.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Authentication authentication = new Authentication(request);
        Notifications  notifications  = ViewUtilities.getNotifications(request);
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

        try {

            request.setAttribute("user", authentication.getUser());
            request.setAttribute("cart", shoppingCart);
            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }

    /**
     * Serves the /preset page where users can see information about the preset with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(request);
        Notifications  notifications  = ViewUtilities.getNotifications(request);
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
            orderFacade.create(authentication.getUser(), shoppingCart, request.getParameter("comment"));
            User user = userFacade.get(authentication.getUser().getId());
            notifications.success(ORDER_SUCCESS);
            user = userFacade.update(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBalance() - shoppingCart.getTotal(),
                    user.getRole());

            authentication.updateUser(user);
            shoppingCart.clear();

            response.sendRedirect("profile");

        } catch (Exception e) {
            notifications.error(ORDER_ERROR);
            response.sendRedirect(ViewUtilities.referer(request, "cart"));
            return;
        }
    }
}
