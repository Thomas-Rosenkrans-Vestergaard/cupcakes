package tvestergaard.cupcakes.view.servlets;

import tvestergaard.cupcakes.logic.OrderFacade;
import tvestergaard.cupcakes.view.Authentication;
import tvestergaard.cupcakes.view.Language;
import tvestergaard.cupcakes.view.Notifications;
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
     * Facade for performing various operations related to orders.
     */
    private final OrderFacade orderFacade = new OrderFacade();

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
            request.setAttribute("orders", orderFacade.get(authentication.getUser()));
            ViewUtilities.attach(request, notifications);
            request.getRequestDispatcher("WEB-INF/profile.jsp").forward(request, response);
        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect(ViewUtilities.referer(request, "shop"));
        }
    }
}
