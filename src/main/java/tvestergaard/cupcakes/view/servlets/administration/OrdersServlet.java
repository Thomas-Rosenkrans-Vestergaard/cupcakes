package tvestergaard.cupcakes.view.servlets.administration;


import tvestergaard.cupcakes.data.orders.Order;
import tvestergaard.cupcakes.logic.OrderFacade;
import tvestergaard.cupcakes.view.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrdersServlet", urlPatterns = "/administration/orders")
public class OrdersServlet extends HttpServlet
{

    /**
     * Facade for performing various operations related to orders.
     */
    private final OrderFacade orderFacade = new OrderFacade();

    private static final String ACTION_UPDATE = "update";

    /**
     * Handles GET requests to /administration/orders.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/orders");
            return;
        }

        try {

            String action = request.getParameter("action");
            ViewUtilities.attach(request, notifications);

            if (ACTION_UPDATE.equals(action)) {
                showUpdate(request, response, notifications);
                return;
            }

            request.setAttribute("orders", orderFacade.get());
            request.getRequestDispatcher("/WEB-INF/administration/read_orders.jsp").forward(request, response);

        } catch (SQLException e) {
            notifications.error("A database error occurred.");
            response.sendRedirect("index");
            return;
        }
    }

    private void showUpdate(HttpServletRequest request, HttpServletResponse response, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("order", orderFacade.get(id));
            request.getRequestDispatcher("/WEB-INF/administration/update_order.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            notifications.warning("Bad id parameter.");
            response.sendRedirect("/orders");
            return;
        } catch (Exception e) {
            notifications.error("An error occurred that prevented the page from being displayed.");
            response.sendRedirect("/orders");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Notifications  notifications  = ViewUtilities.getNotifications(request);
        Authentication authentication = new Authentication(request);
        String         action         = request.getParameter("action");

        if (!authentication.isAdministrator()) {
            notifications.error(Language.ERROR_ACCESS_ADMINISTRATOR);
            response.sendRedirect("../login?from=administration/orders");
            return;
        }

        try {

            if (ACTION_UPDATE.equals(action)) {
                handleUpdate(request, response, authentication, notifications);
                return;
            }

        } catch (Exception e) {
            notifications.error(Language.GENERAL_ERROR_RENDER);
            response.sendRedirect("presets");
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Notifications notifications)
            throws ServletException, IOException, SQLException
    {
        Parameters parameters = new Parameters(request);

        if (parameters.isEmpty("id")
            || parameters.notInt("id")
            || parameters.notPresent("comment")
            || parameters.isEmpty("comment")
            || parameters.notPresent("status")
            || parameters.isEmpty("status")
            || parameters.notInt("status")) {
            notifications.error("Incomplete form data.");
            response.sendRedirect(ViewUtilities.referer(request, "orders"));
            return;
        }

        orderFacade.update(parameters.getInt("id"),
                           authentication.getUser(),
                           parameters.getString("comment"),
                           Order.Status.fromCode(parameters.getInt("status")));

        response.sendRedirect("orders?action=update&id=" + parameters.getInt("id"));
    }
}