package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.Authentication;
import tvestergaard.cupcakes.Language;
import tvestergaard.cupcakes.Notifications;
import tvestergaard.cupcakes.ShoppingCart;
import tvestergaard.cupcakes.database.PrimaryDatabase;
import tvestergaard.cupcakes.database.bottoms.Bottom;
import tvestergaard.cupcakes.database.bottoms.BottomDAO;
import tvestergaard.cupcakes.database.bottoms.MysqlBottomDAO;
import tvestergaard.cupcakes.database.toppings.MysqlToppingDAO;
import tvestergaard.cupcakes.database.toppings.Topping;
import tvestergaard.cupcakes.database.toppings.ToppingDAO;
import tvestergaard.cupcakes.view.Parameters;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Serves the /cart page where authorized users can see the products in their cart. Handles POST requests for adding new
 * items to the shopping cart of an authorized user.
 */
@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet
{

    private final PrimaryDatabase source     = new PrimaryDatabase();
    private final BottomDAO       bottomDAO  = new MysqlBottomDAO(source);
    private final ToppingDAO      toppingDAO = new MysqlToppingDAO(source);

    /**
     * Serves the /custom page where users can create their own cupcake.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAuthenticated()) {
            notifications.error(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login?from=cart");
            return;
        }

        try {
            ViewUtilities.attach(request, notifications);
            request.setAttribute("cart", request.getSession().getAttribute("cart"));
            request.getRequestDispatcher("WEB-INF/cart.jsp").forward(request, response);
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }

    private static final String PARAMETER_BOTTOM   = "bottom";
    private static final String PARAMETER_TOPPING  = "topping";
    private static final String PARAMETER_QUANTITY = "quantity";

    /**
     * Serves the /preset page where users can see information about the bottom with the provided id.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAuthenticated()) {
            notifications.error(Language.ERROR_ACCESS_USER);
            response.sendRedirect("login?from=cart");
            return;
        }

        Parameters parameters = new Parameters(request);
        if (parameters.notPresent(PARAMETER_BOTTOM)
                || parameters.notPositiveInt(PARAMETER_BOTTOM)
                || parameters.notPresent(PARAMETER_TOPPING)
                || parameters.notPositiveInt(PARAMETER_TOPPING)
                || parameters.notPresent(PARAMETER_QUANTITY)
                || parameters.notPositiveInt(PARAMETER_TOPPING)) {
            notifications.error(Language.INCOMPLETE_FORM_POST);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
            return;
        }

        try {

            Bottom  bottom  = bottomDAO.get(parameters.getInt(PARAMETER_BOTTOM));
            Topping topping = toppingDAO.get(parameters.getInt(PARAMETER_TOPPING));

            if (bottom == null) {
                notifications.error("Bottom doesn't exist.");
                response.sendRedirect(ViewUtilities.referer(request, "shop"));
                return;
            }

            if (topping == null) {
                notifications.error("Bottom doesn't exist.");
                response.sendRedirect(ViewUtilities.referer(request, "shop"));
                return;
            }

            ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
            shoppingCart.addItem(new ShoppingCart.Item(bottom, topping, parameters.getInt("quantity")));
            response.sendRedirect("cart");

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}