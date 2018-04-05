package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.logic.BottomFacade;
import tvestergaard.cupcakes.logic.ToppingFacade;
import tvestergaard.cupcakes.view.Language;
import tvestergaard.cupcakes.view.Notifications;
import tvestergaard.cupcakes.view.Parameters;
import tvestergaard.cupcakes.view.ViewUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomServlet", urlPatterns = "/custom")
public class CustomServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to bottoms.
     */
    private final BottomFacade bottomFacade = new BottomFacade();

    /**
     * Facade for performing various operations related to toppings.
     */
    private final ToppingFacade toppingFacade = new ToppingFacade();

    /**
     * Serves the /custom page where users can create their own cupcake. Parameters 'bottom' and 'topping' can be provided The url parameters 'bottom' and 'topping' can
     * be use to fill the selection.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Notifications notifications = ViewUtilities.getNotifications(request);
        Parameters    parameters    = new Parameters(request);

        try {

            request.setAttribute("bottoms", bottomFacade.get());
            request.setAttribute("toppings", toppingFacade.get());


            if (parameters.isPresent("bottom") && parameters.isInt("bottom"))
                request.setAttribute("selectedBottom", parameters.getInt("bottom"));
            if (parameters.isPresent("topping") && parameters.isInt("topping"))
                request.setAttribute("selectedTopping", parameters.getInt("topping"));

            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/custom.jsp").forward(request, response);

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }

    /**
     * Serves the /custom page where users can create their own cupcake. The url parameters 'bottom' and 'topping' can
     * be use to fill the selection.
     *
     * @param request  The request.
     * @param response The response.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
