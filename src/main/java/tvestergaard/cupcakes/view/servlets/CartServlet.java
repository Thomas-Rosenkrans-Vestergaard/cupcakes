package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.logic.NoSuchBottomException;
import tvestergaard.cupcakes.logic.NoSuchToppingException;
import tvestergaard.cupcakes.logic.ShoppingCart;
import tvestergaard.cupcakes.logic.ShoppingCartFacade;
import tvestergaard.cupcakes.view.*;

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

    /**
     * Facade for performing various operations related to shopping carts.
     */
    private final ShoppingCartFacade shoppingCartFacade = new ShoppingCartFacade();

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

        ViewUtilities.attach(request, notifications);
        request.setAttribute("cart", request.getSession().getAttribute("cart"));
        request.getRequestDispatcher("WEB-INF/cart.jsp").forward(request, response);
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

            int bottom   = parameters.getInt("bottom");
            int topping  = parameters.getInt("topping");
            int quantity = parameters.getInt("quantity");

            ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
            shoppingCartFacade.addToCart(shoppingCart, bottom, topping, quantity);
            notifications.success("The item was added to the cart.");
            response.sendRedirect("cart");

        } catch (NoSuchBottomException e) {
            notifications.error("No such bottom with the provided id.");
            response.sendRedirect("shop");
        } catch (NoSuchToppingException e) {
            notifications.error("No such topping with the provided id.");
            response.sendRedirect("shop");
        }
    }
}